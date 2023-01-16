package pl.lodz.p.it.spjava.e12.appstore.web.account;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;

@Named(value = "accountCreateNewByAdminConfirmBean")
@RequestScoped
public class AccountCreateNewByAdminConfirmBean {

    @Inject
    private AccountController accountController;

    private AccountDTO account;

    public AccountDTO getAccount() {
        return account;
    }

    public String createNewAccountByAdmin() {
        return accountController.createNewAccountByAdmin();
    }

    @PostConstruct
    private void initBean() {
        account = accountController.getAccountForCreateByAdmin();
    }

    public AccountCreateNewByAdminConfirmBean() {
    }

}
