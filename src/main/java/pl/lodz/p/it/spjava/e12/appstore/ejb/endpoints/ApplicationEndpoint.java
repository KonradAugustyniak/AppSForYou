package pl.lodz.p.it.spjava.e12.appstore.ejb.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.ejb.managers.ApplicationManager;
import pl.lodz.p.it.spjava.e12.appstore.exception.AppBaseException;
import pl.lodz.p.it.spjava.e12.appstore.exception.ApplicationException;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;
import pl.lodz.p.it.spjava.e12.appstore.model.Application;
import pl.lodz.p.it.spjava.e12.appstore.model.Application.OperatingSystem;
import pl.lodz.p.it.spjava.e12.appstore.model.FileData;
import pl.lodz.p.it.spjava.e12.appstore.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
public class ApplicationEndpoint {

    @Inject
    private ApplicationManager applicationManager;

    private Application appStatus;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    /*
     * Zwraca liste aplikacji
     */
    @PermitAll
    public List<ApplicationDTO> getAllApps() {
        return DTOConverter.createListAppsDTOfromEntity(applicationManager.getAllApps());
    }

    /*
     * Zwraca liste aplikacji dla zalogowanego usera
     */
    @RolesAllowed({"User"})
    public List<ApplicationDTO> getMyApplications() {
        return DTOConverter.createListAppsDTOfromEntity(applicationManager.getMyApplications());
    }

    /*
     * Zwraca liste dostepnych oS
     */
    @PermitAll
    public List<String> getOperatingSystems() {
        ArrayList<String> osList = new ArrayList<>();
        for (OperatingSystem os : OperatingSystem.values()) {
            osList.add(os.toString());
        }
        return osList;
    }

    /*
     * Konwertuje obiekt OperatingSystem do Enum
     */
    @PermitAll
    public OperatingSystem convertOperatingSystemToEnum(String osString) {
        return OperatingSystem.valueOf(osString);
    }

    /*
     * Dodanie nowej aplikacji
     */
    @RolesAllowed({"User", "Moderator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void uploadNewApplication(ApplicationDTO applicationDTO) throws AppBaseException {

        Application application = new Application();
        setDataToNewApplication(applicationDTO, application);

        FileData fileData = new FileData(application, application.getFileData().getFileName(), application.getFileData().getFileSize(),
                application.getFileData().getFileType());

        application.setFileData(fileData);

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                applicationManager.uploadNewApplication(application);
                rollbackTX = applicationManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw ApplicationException.uploadApplicationExceptionWithTxRetryRollback();
        }

    }

    /*
     * Znajduje autora aplikacji po id i przypisuje do rekordu aplikacji
     * Dostarcza dane z DTO do encji
     */
    private void setDataToNewApplication(ApplicationDTO applicationDTO, Application application) {
        rewriteDataForNewApplicationDTOtoEntity(applicationDTO, application);
        Account account = new Account(applicationManager.findIdByUserLogin());
        application.setApplicationAuthor(account);
        application.setApplicationFile(applicationDTO.getApplicationFile());
        application.setOperatingSystem(convertOperatingSystemToEnum(applicationDTO.getOperatingSystem()));
        FileData fileData = new FileData(applicationDTO.getFileData().getFileName(), applicationDTO.getFileData().getFileSize(), applicationDTO.getFileData().getFileType());
        application.setFileData(fileData); // to dodane
    }

    /*
     * Dostarcza dane z DTO do encji
     */
    private static void rewriteDataForNewApplicationDTOtoEntity(ApplicationDTO applicationDTO, Application application) {
        application.setApplicationName(applicationDTO.getApplicationName());
        application.setDescription(applicationDTO.getDescription());
        application.setApplicationVersion(applicationDTO.getApplicationVersion());
    }

    /*
     * Usuwa rekord aplikacji
     */
    @RolesAllowed({"User", "Moderator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void deleteApplication(ApplicationDTO applicationDTO) throws AppBaseException {

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                applicationManager.deleteApplication(applicationDTO);
                rollbackTX = applicationManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw ApplicationException.deleteApplicationExceptionWithTxRetryRollback();
        }

    }

    /*
     * Wyszukuje rekord aplikacji do edycji
     */
    public ApplicationDTO findApplicationToEdit(ApplicationDTO applicationDTO) {
        appStatus = applicationManager.findApplicationToEdit(applicationDTO);
        return DTOConverter.createAppDTOfromEntity(appStatus);
    }

    /*
     * Zapisuje zmieniony rekord aplikacji
     */
    public void saveApplicationAfterEdit(ApplicationDTO applicationDTO) {
        if (null == appStatus) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        appStatus.setOperatingSystem(convertOperatingSystemToEnum(applicationDTO.getOperatingSystem()));

        FileData fileData = new FileData(appStatus, applicationDTO.getFileData().getFileName(), applicationDTO.getFileData().getFileSize(),
                applicationDTO.getFileData().getFileType());
        appStatus.setFileData(fileData);

        applicationManager.editApplication(applicationDTO, appStatus);
        appStatus = null;
    }

    /*
     * Znajduje rekord aplikacji 
     * Dostarcza dane z encji do DTO
     */
    public ApplicationDTO findApplicationFileToDownload(ApplicationDTO applicationDTO) {
        appStatus = applicationManager.findApplicationFileToDownload(applicationDTO);
        return DTOConverter.createAppDTOfromEntity(appStatus);
    }

}
