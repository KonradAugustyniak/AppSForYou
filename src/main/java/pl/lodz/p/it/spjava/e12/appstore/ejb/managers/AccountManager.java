package pl.lodz.p.it.spjava.e12.appstore.ejb.managers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.appstore.ejb.facades.AccountFacade;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.exception.AppBaseException;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private AccountFacade accountFacade;

    public void registerNewAccount(Account account) throws AppBaseException {
        accountFacade.create(account);
    }

    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    @RolesAllowed({"User", "Moderator", "Administrator"})
    public Account getMyAccount() {
        return accountFacade.findLogin(getMyLogin());
    }

    @RolesAllowed({"User", "Moderator", "Administrator"})
    public String getMyLogin() throws IllegalStateException {
        return sctx.getCallerPrincipal().getName();
    }

    public void deleteAccount(AccountDTO accountDTO) throws AppBaseException {
        accountFacade.remove(accountFacade.find(accountDTO.getUserId()));
    }

    public Account findAccountToEdit(AccountDTO accountDTO) {
        return accountFacade.find(accountDTO.getUserId());
    }

    public void editAccount(AccountDTO accountDTO, Account accountStatus) {
        accountStatus.setName(accountDTO.getName());
        accountStatus.setSurname(accountDTO.getSurname());
        accountStatus.setEmail(accountDTO.getEmail());
        accountStatus.setPassword(accountDTO.getPassword());
        accountFacade.edit(accountStatus);
    }

}
