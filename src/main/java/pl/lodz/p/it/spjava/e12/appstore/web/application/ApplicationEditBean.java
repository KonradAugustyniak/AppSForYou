package pl.lodz.p.it.spjava.e12.appstore.web.application;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.file.UploadedFile;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;
import pl.lodz.p.it.spjava.e12.appstore.model.FileData;

@Named(value = "applicationEditBean")
@ViewScoped
public class ApplicationEditBean implements Serializable {

    public ApplicationEditBean() {
    }

    @Inject
    private ApplicationController applicationController;

    private UploadedFile file;

    private byte[] choosenApplicationFile;

    private String choosenApplicationFileName;

    private Long choosenApplicationFileSize;

    private String choosenApplicationFileType;

    private FacesMessage message;

    private ApplicationDTO app = new ApplicationDTO();

    public ApplicationDTO getApp() {
        return app;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public byte[] getChoosenApplicationFile() {
        return choosenApplicationFile;
    }

    public void setChoosenApplicationFile(byte[] choosenApplicationFile) {
        this.choosenApplicationFile = choosenApplicationFile;
    }

    public String getChoosenApplicationFileName() {
        return choosenApplicationFileName;
    }

    public void setChoosenApplicationFileName(String choosenApplicationFileName) {
        this.choosenApplicationFileName = choosenApplicationFileName;
    }

    public Long getChoosenApplicationFileSize() {
        return choosenApplicationFileSize;
    }

    public void setChoosenApplicationFileSize(Long choosenApplicationFileSize) {
        this.choosenApplicationFileSize = choosenApplicationFileSize;
    }

    public String getChoosenApplicationFileType() {
        return choosenApplicationFileType;
    }

    public void setChoosenApplicationFileType(String choosenApplicationFileType) {
        this.choosenApplicationFileType = choosenApplicationFileType;
    }

    @PostConstruct
    private void init() {
        app = applicationController.getAppToEdit();
    }

    /*
     * Wskazanie pliku aplikacji przy jej edycji
     */
    public void uploadApplicationFile() {

        if (file != null) {
            message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            choosenApplicationFile = file.getContent();
            choosenApplicationFileName = file.getFileName();
            choosenApplicationFileSize = file.getSize();
            choosenApplicationFileType = file.getContentType();
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Successful", "Please choose the file");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    /*
     * Weryfikacja formularza oraz dodania pliku aplikacji - edycja istniejacej aplikacji
     */
    public String saveNewDataForEditApplication() {

        if (choosenApplicationFile != null && choosenApplicationFile.length > 0) {
            app.setApplicationFile(choosenApplicationFile);
            app.setFileData(new FileData(choosenApplicationFileName, choosenApplicationFileSize, choosenApplicationFileType));
            return applicationController.saveApplicationAfterEdit(app);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Successful", "You need to add a file");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }

    }

}
