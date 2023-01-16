package pl.lodz.p.it.spjava.e12.appstore.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "APPS")
@NamedQuery(name = "Application.findLoggedInUserApps", query = "SELECT auth FROM Application auth WHERE auth.applicationAuthor.login=:login")
public class Application extends AbstractEntity implements Serializable {

    @Id
    @Column(name = "APP_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @NotEmpty
    @Size(min = 3, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(name = "APP_NAME", length = 64, nullable = false)
    private String applicationName;

    @NotEmpty
    @Size(min = 3, message = "{constraint.string.length.notinrange}")
    @Column(nullable = false)
    private String description;

    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "USER_ID", nullable = false)
    @ManyToOne()
    private Account applicationAuthor;

    public enum OperatingSystem {
        Android,
        iOS,
        macOS,
        Windows,
        Other
    }

    @Column(name = "OPERATING_SYSTEM", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;

    @Lob
    @Column(name = "APP_FILE", nullable = false)
    @NotNull(message = "{constraint.notnull}")
    private byte[] applicationFile;

    @NotEmpty
    @Pattern(regexp = "(\\d+\\.)(\\d+\\.)(\\d+\\.)(\\d)") // ^(\d+\.)?(\d+\.)?(\*|\d+)$
    @Size(min = 7, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(name = "APP_VERSION", nullable = false)
    private String applicationVersion;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FileData fileData;

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

    public Account getApplicationAuthor() {
        return applicationAuthor;
    }

    public void setApplicationAuthor(Account applicationAuthor) {
        this.applicationAuthor = applicationAuthor;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public byte[] getApplicationFile() {
        return applicationFile;
    }

    public void setApplicationFile(byte[] applicationFile) {
        this.applicationFile = applicationFile;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }

    public Application() {
    }

    public Application(String applicationName, String description, Account applicationAuthor, OperatingSystem operatingSystem, String applicationVersion) {
        this.applicationName = applicationName;
        this.description = description;
        this.applicationAuthor = applicationAuthor;
        this.operatingSystem = operatingSystem;
        this.applicationVersion = applicationVersion;
    }

    @Override
    public Long getId() {
        return applicationId;
    }

    @Override
    protected Object getBusinessKey() {
        return applicationName;
    }

}
