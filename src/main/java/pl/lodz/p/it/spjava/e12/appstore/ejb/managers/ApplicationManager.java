package pl.lodz.p.it.spjava.e12.appstore.ejb.managers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e12.appstore.dto.ApplicationDTO;
import pl.lodz.p.it.spjava.e12.appstore.ejb.facades.AccountFacade;
import pl.lodz.p.it.spjava.e12.appstore.ejb.facades.ApplicationFacade;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.exception.AppBaseException;
import pl.lodz.p.it.spjava.e12.appstore.model.Application;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ApplicationManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private ApplicationFacade applicationFacade;

    @Inject
    private AccountFacade accountFacade;

    @RolesAllowed({"User", "Moderator"})
    public void uploadNewApplication(Application application) throws AppBaseException {
        applicationFacade.create(application);
    }

    public List<Application> getAllApps() {
        return applicationFacade.findAll();
    }

    public Long findIdByUserLogin() {
        return accountFacade.getLoggedInUserId(sctx.getCallerPrincipal().getName());
    }

    @RolesAllowed({"User"})
    public List<Application> getMyApplications() {
        return applicationFacade.findApplicationsListForUser(sctx.getCallerPrincipal().getName());
    }

    @RolesAllowed({"User", "Moderator"})
    public void deleteApplication(ApplicationDTO applicationDTO) throws AppBaseException {
        Application foundedApplications = applicationFacade.find(applicationDTO.getApplicationId());
        foundedApplications.getApplicationAuthor().getUploadedApplicationList().remove(foundedApplications);
        applicationFacade.remove(foundedApplications);
    }

    public Application findApplicationToEdit(ApplicationDTO applicationDTO) {
        return applicationFacade.find(applicationDTO.getApplicationId());
    }

    public void editApplication(ApplicationDTO applicationDTO, Application appStatus) {
        appStatus.setApplicationName(applicationDTO.getApplicationName());
        appStatus.setDescription(applicationDTO.getDescription());
        appStatus.setApplicationVersion(applicationDTO.getApplicationVersion());
        appStatus.setApplicationFile(applicationDTO.getApplicationFile());
        applicationFacade.edit(appStatus);
    }

    /*
     * Znajduje rekord aplikacji na podstawie jej id celem identyfikacji prawidłowego pliku do ściągnięcia
     */
    public Application findApplicationFileToDownload(ApplicationDTO applicationDTO) {
        return applicationFacade.find(applicationDTO.getApplicationId());
    }

}
