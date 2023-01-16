package pl.lodz.p.it.spjava.e12.appstore.ejb.facades;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.model.FileData;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FileDataFacade extends AbstractFacade<FileData> {

    @PersistenceContext(unitName = "AppStoreJavaDB_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FileDataFacade() {
        super(FileData.class);
    }

    @Override
    public void create(FileData entity) {
        super.create(entity);
        em.flush();
    }

}
