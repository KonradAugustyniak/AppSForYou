package pl.lodz.p.it.spjava.e12.appstore.web.application;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;

@Named(value = "applicationUploadConfirmPageBean")
@RequestScoped
public class ApplicationUploadConfirmPageBean {

    @Inject
    private ApplicationController applicationController;

    private ApplicationDTO app;

    public ApplicationDTO getApp() {
        return app;
    }

    public String uploadNewApplication() {
        return applicationController.uploadNewApplication();
    }

    @PostConstruct
    private void initBean() {
        app = applicationController.getAppToUpload();
    }

    public ApplicationUploadConfirmPageBean() {
    }

}
