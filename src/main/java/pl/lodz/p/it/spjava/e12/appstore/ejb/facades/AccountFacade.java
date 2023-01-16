package pl.lodz.p.it.spjava.e12.appstore.ejb.facades;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;
import pl.lodz.p.it.spjava.e12.appstore.model.Account_;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    private static final Logger LOG = Logger.getLogger(AccountFacade.class.getName());

    @PersistenceContext(unitName = "AppStoreJavaDB_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public Account findLoginByUserId(Long userId) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findLoginByUserId", Account.class);
        tq.setParameter("userId", userId);
        Account account = tq.getSingleResult();
        em.refresh(account);
        return account;
    }

    @RolesAllowed({"User", "Moderator", "Administrator"})
    public Account findLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Account_.login), login));
        TypedQuery<Account> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

    public Long getLoggedInUserId(String login) {
        return findLogin(login).getId();
    }

    // Ta metoda jest używana przez implementację uwierzytelniania zgodnie z Soteria
    @ExcludeClassInterceptors //Nie chcemy ujawniać w dziennikach skrótu hasła
    @RolesAllowed("AUTHENTICATOR") //"Zwykłe" role nie mają tu dostępu. Musi pośredniczyć odpowiedni endpoint opisany jako @RunAs("AUTHENTICATOR").
    public Account findLoginAndPassInAccountList(String login, String pass) {
        if (null == login || null == pass || login.isEmpty() || pass.isEmpty()) {
            return null;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(from.get(Account_.login), login));
        criteria = cb.and(criteria, cb.equal(from.get(Account_.password), pass));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> tq = em.createQuery(query);

        try {
            return tq.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Authentication for login: {0} failed with: {1}", new Object[]{login, ex});
        }
        return null;

    }

}
