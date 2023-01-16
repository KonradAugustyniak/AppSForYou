package pl.lodz.p.it.spjava.e12.appstore.ejb.facades;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.model.Application;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ApplicationFacade extends AbstractFacade<Application> {

    @PersistenceContext(unitName = "AppStoreJavaDB_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicationFacade() {
        super(Application.class);
    }

    @RolesAllowed({"User"})
    public List<Application> findApplicationsListForUser(String login) {
        TypedQuery<Application> tq = em.createNamedQuery("Application.findLoggedInUserApps", Application.class);
        tq.setParameter("login", login);

        return tq.getResultList();
    }

}
