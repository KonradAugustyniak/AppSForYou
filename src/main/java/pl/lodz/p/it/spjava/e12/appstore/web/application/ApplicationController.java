package pl.lodz.p.it.spjava.e12.appstore.web.application;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;
import pl.lodz.p.it.spjava.e12.appstore.ejb.endpoints.ApplicationEndpoint;
import pl.lodz.p.it.spjava.e12.appstore.exception.AppBaseException;
import pl.lodz.p.it.spjava.e12.appstore.exception.ApplicationException;
import pl.lodz.p.it.spjava.e12.appstore.utils.ContextUtils;

@Named(value = "applicationController")
@SessionScoped
public class ApplicationController implements Serializable {

    @Inject
    private ApplicationEndpoint applicationEndpoint;

    private ApplicationDTO appToUpload;

    private ApplicationDTO appToEdit;

    public ApplicationDTO getAppToUpload() {
        return appToUpload;
    }

    public ApplicationDTO getAppToEdit() {
        return appToEdit;
    }

    /*
     * Rozpoczyna proces dodawania rekordu aplikacji
     */
    public String uploadNewApplication() {
        try {
            applicationEndpoint.uploadNewApplication(appToUpload);
            appToUpload = null;
            return "appsUploadedSuccessful";
        } catch (ApplicationException ae) {
            if (ApplicationException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("applicationName", ApplicationException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji uploadNewApplication wyjatku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji uploadNewApplication wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    /*
     * Potwierdzenie dodania rekordu aplikacji
     */
    public String confirmAppToUpload(ApplicationDTO app) {
        this.appToUpload = app;
        return "appsUploadConfirm";
    }

    public List<ApplicationDTO> getAllApps() {
        return applicationEndpoint.getAllApps();
    }

    public List<ApplicationDTO> getMyApplications() {
        return applicationEndpoint.getMyApplications();
    }

    public List<String> getOperatingSystemsList() {
        return applicationEndpoint.getOperatingSystems();
    }

    /*
     * Wybiera rekord aplikacji do edycji
     */
    public String selectApplicationToEdit(ApplicationDTO applicationDTO) {
        appToEdit = applicationEndpoint.findApplicationToEdit(applicationDTO);
        return "appsEdit";
    }

    /*
     * Rozpoczyna proces zapisywania rekordu aplikacji po edycji
     */
    public String saveApplicationAfterEdit(ApplicationDTO applicationDTO) {
        applicationEndpoint.saveApplicationAfterEdit(applicationDTO);
        return "appsEditSuccessful";
    }

    /*
     * Rozpoczyna proces usuwania rekordu aplikacji
     */
    public String selectApplicationToDelete(ApplicationDTO applicationDTO) {
        try {
            applicationEndpoint.deleteApplication(applicationDTO);
            return "appsDeleteSuccessful";
        } catch (ApplicationException ae) {
            if (ApplicationException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("applicationName", ApplicationException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji selectApplicationToDelete wyjatku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji selectApplicationToDelete wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    /*
     * Wybiera plik ze znalezionego rekordu aplikacji
     */
    public byte[] selectFileToDownload(ApplicationDTO applicationDTO) {
        return applicationEndpoint.findApplicationFileToDownload(applicationDTO).getApplicationFile();
    }

    public ApplicationController() {
    }

}
