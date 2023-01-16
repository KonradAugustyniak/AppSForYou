package pl.lodz.p.it.spjava.e12.appstore.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILESDATA")
public class FileData implements Serializable {

    @Id
    @Column(name = "FILE_APP_ID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "FILE_APP_ID")
    private Application file;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "FILE_TYPE")
    private String fileType;

    public Application getFile() {
        return file;
    }

    public void setFile(Application file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public FileData() {
    }

    public FileData(String fileName, Long fileSize, String fileType) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public FileData(Application file, String fileName, Long fileSize, String fileType) {
        this.file = file;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

}
