package pl.lodz.p.it.spjava.e12.appstore.web.account;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.appstore.utils.ContextUtils;

@Named(value = "accountEditBean")
@RequestScoped
public class AccountEditBean implements Serializable {

    public AccountEditBean() {
    }

    @Inject
    private AccountController accountController;

    private AccountDTO account = new AccountDTO();

    private String repeatPassword = "";

    public AccountDTO getAccount() {
        return account;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @PostConstruct
    private void init() {
        account = accountController.getAccountToEdit();
    }

    public String saveNewDataForEditAccount() {
        if (!(repeatPassword.equals(account.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("editForm:repeatPassword", "passwords.not.matching");
            return null;
        }
        return accountController.saveAccountAfterEdit(account);
    }

}
