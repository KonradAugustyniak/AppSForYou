package pl.lodz.p.it.spjava.e12.appstore.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import pl.lodz.p.it.spjava.e12.appstore.model.FileData;

public class ApplicationDTO {

    private Long applicationId;

    private AccountDTO login;

    @NotEmpty
    @Size(min = 3, max = 64, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String applicationName;

    @NotEmpty
    @Size(min = 3, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String description;

    @NotEmpty
    @NotNull
    private String operatingSystem;

    @NotEmpty
    @Pattern(regexp = "(\\d+\\.)(\\d+\\.)(\\d+\\.)(\\d)", message = "{constraint.string.incorrectchar}")
    @Size(min = 7, max = 32, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String applicationVersion;

    @NotEmpty
    @NotNull(message = "{constraint.notnull}")
    private byte[] applicationFile;
    
    private FileData fileData;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long applicationId, FileData fileData) {
        this.applicationId = applicationId;
        this.fileData = fileData;
    }

    public ApplicationDTO(Long applicationId, String applicationName, String description, String operatingSystem, String applicationVersion, 
            byte[] applicationFile, AccountDTO login) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.description = description;
        this.operatingSystem = operatingSystem;
        this.applicationVersion = applicationVersion;
        this.applicationFile = applicationFile;
        this.login = login;
    }

    public ApplicationDTO(Long applicationId, AccountDTO login, String applicationName, String description, String operatingSystem, 
            String applicationVersion, byte[] applicationFile, FileData fileData) {
        this.applicationId = applicationId;
        this.login = login;
        this.applicationName = applicationName;
        this.description = description;
        this.operatingSystem = operatingSystem;
        this.applicationVersion = applicationVersion;
        this.applicationFile = applicationFile;
        this.fileData = fileData;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public byte[] getApplicationFile() {
        return applicationFile;
    }

    public void setApplicationFile(byte[] applicationFile) {
        this.applicationFile = applicationFile;
    }

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }

    public AccountDTO getLogin() {
        return login;
    }

    public void setLogin(AccountDTO login) {
        this.login = login;
    }

    public Long getApplicationId() {
        return applicationId;
    }

}
