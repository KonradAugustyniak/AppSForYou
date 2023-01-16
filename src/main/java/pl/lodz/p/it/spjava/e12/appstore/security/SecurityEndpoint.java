package pl.lodz.p.it.spjava.e12.appstore.security;

import javax.annotation.security.RunAs;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.ejb.facades.AccountFacade;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@RunAs("AUTHENTICATOR")
public class SecurityEndpoint {

    @Inject
    private AccountFacade accountFacade;

    public Account findAccountForAuthenticationConditions(String login, String password) {
        return accountFacade.findLoginAndPassInAccountList(login, password);
    }

}
