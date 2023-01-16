package pl.lodz.p.it.spjava.e12.appstore.web.application;

import java.io.ByteArrayInputStream;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;

@Named(value = "applicationListDownloadFilePageBean")
@ApplicationScoped
public class ApplicationListDownloadFilePageBean implements Serializable {

    public ApplicationListDownloadFilePageBean() {
    }

    @Inject
    private ApplicationController applicationController;

    private ApplicationDTO app = new ApplicationDTO();

    @PostConstruct
    private void initModel() {
    }

    public void refresh() {
        initModel();
    }

    public String getApplication(ApplicationDTO app) {
        this.app = app;
        return null;
    }

    public StreamedContent getFile() {
        byte[] appFile = applicationController.selectFileToDownload(app);
        return DefaultStreamedContent.builder()
                .name("test.txt")
                .contentType("text/plain")
                .stream(() -> new ByteArrayInputStream(appFile))
                .build();
    }

}
