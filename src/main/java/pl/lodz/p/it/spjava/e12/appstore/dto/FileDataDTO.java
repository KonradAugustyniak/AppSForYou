package pl.lodz.p.it.spjava.e12.appstore.dto;

public class FileDataDTO {

    private String fileName;

    private Long fileSize;

    private String fileType;

    public FileDataDTO() {
    }

    public FileDataDTO(String fileName, Long fileSize, String fileType) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
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

}
