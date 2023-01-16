package pl.lodz.p.it.spjava.e12.appstore.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;
import pl.lodz.p.it.spjava.e12.appstore.dto.FileDataDTO;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;
import pl.lodz.p.it.spjava.e12.appstore.model.Application;
import pl.lodz.p.it.spjava.e12.appstore.model.FileData;

public class DTOConverter {

    /*
     * Konwertuje pojedyncze konto z encji na DTO
     */
    public static AccountDTO createAccountDTOfromEntity(Account account) {
        return null == account ? null : new AccountDTO(
                account.getId(),
                account.getName(),
                account.getSurname(),
                account.getEmail(),
                account.getLogin(),
                account.getAccountType().toString());
    }

    /*
     * Konwertuje liste kont z encji na DTO
     */
    public static List<AccountDTO> createListAccountsDTOfromEntity(List<Account> accounts) {
        return null == accounts ? null : accounts.stream()
                .filter(Objects::nonNull)
                .map(elt -> DTOConverter.createAccountDTOfromEntity(elt))
                .collect(Collectors.toList());
    }

    /*
     * Konwertuje pojedyncza aplikacje z encji na DTO
     */
   /* public static ApplicationDTO createAppDTOfromEntity(Application application) {
        return null == application ? null : new ApplicationDTO(
                application.getId(),
                application.getApplicationName(),
                application.getDescription(),
                application.getOperatingSystem().toString(),
                application.getApplicationVersion(),
                application.getApplicationFile(),
                createAccountDTOfromEntity(application.getApplicationAuthor()));
    } */
    
    public static ApplicationDTO createAppDTOfromEntity(Application application) {
        return null == application ? null : new ApplicationDTO(
                application.getId(),
                createAccountDTOfromEntity(application.getApplicationAuthor()),
                application.getApplicationName(),
                application.getDescription(),
                application.getOperatingSystem().toString(),
                application.getApplicationVersion(),
                application.getApplicationFile(),
                application.getFileData()
                );
    }

    /*
     * Konwertuje liste aplikacji z encji na DTO
     */
    public static List<ApplicationDTO> createListAppsDTOfromEntity(List<Application> applications) {
        return null == applications ? null : applications.stream()
                .filter(Objects::nonNull)
                .map(elt -> DTOConverter.createAppDTOfromEntity(elt))
                .collect(Collectors.toList());
    }

    /*
     * Konwertuje dane pliku aplikacji z encji na DTO
     */
    public static FileDataDTO createDataFileDTOfromEntity(FileData filesData) {
        return null == filesData ? null : new FileDataDTO(
                filesData.getFileName(), filesData.getFileSize(), filesData.getFileType());
    }

    public static List<FileDataDTO> createDataFilesDTOfromEntity(List<FileData> filesData) {
        return null == filesData ? null : filesData.stream()
                .filter(Objects::nonNull)
                .map(elt -> DTOConverter.createDataFileDTOfromEntity(elt))
                .collect(Collectors.toList());
    }

}
