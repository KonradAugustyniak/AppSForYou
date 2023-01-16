package pl.lodz.p.it.spjava.e12.appstore.web.application;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;

@Named(value = "applicationEditLoggedInUserBean")
@RequestScoped
public class ApplicationEditLoggedInUserBean {

    public ApplicationEditLoggedInUserBean() {
    }

    @Inject
    private ApplicationController applicationController;

    private List<ApplicationDTO> listApps;

    public List<ApplicationDTO> getListApps() {
        return listApps;
    }

    @PostConstruct
    private void init() {
        listApps = applicationController.getMyApplications();
    }

    public void refresh() {
        init();
    }

}
