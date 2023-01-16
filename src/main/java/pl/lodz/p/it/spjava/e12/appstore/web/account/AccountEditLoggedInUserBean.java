package pl.lodz.p.it.spjava.e12.appstore.web.account;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;

@Named(value = "accountEditLoggedInUserBean")
@RequestScoped
public class AccountEditLoggedInUserBean implements Serializable {

    public AccountEditLoggedInUserBean() {
    }

    @Inject
    private AccountController accountController;

    private AccountDTO account = new AccountDTO();

    public AccountDTO getAccount() {
        return account;
    }

    @PostConstruct
    private void init() {
        account = accountController.getMyAccount();
    }

    public void refresh() {
        init();
    }

}
