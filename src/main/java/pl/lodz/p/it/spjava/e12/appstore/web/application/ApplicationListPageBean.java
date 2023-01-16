package pl.lodz.p.it.spjava.e12.appstore.web.application;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;

@Named(value = "applicationListPageBean")
@ViewScoped
public class ApplicationListPageBean implements Serializable {

    public ApplicationListPageBean() {
    }

    @Inject
    private ApplicationController applicationController;

    private List<ApplicationDTO> applications;

    private List<String> operatingSystemsList;

    @PostConstruct
    private void initModel() {
        applications = applicationController.getAllApps();
        operatingSystemsList = applicationController.getOperatingSystemsList();
    }

    public List<ApplicationDTO> getAllApps() {
        return applications;
    }

    public List<String> getOperatingSystemsList() {
        return operatingSystemsList;
    }

    public void refresh() {
        initModel();
    }

    public String editApplication(ApplicationDTO app) {
        return applicationController.selectApplicationToEdit(app);
    }

    public String deleteApplication(ApplicationDTO app) {
        return applicationController.selectApplicationToDelete(app);
    }

}
