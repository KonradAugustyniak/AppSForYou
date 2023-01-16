package pl.lodz.p.it.spjava.e12.appstore.web.account;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.appstore.utils.ContextUtils;

@Named(value = "accountRegisterPageBean")
@RequestScoped
public class AccountRegisterPageBean implements Serializable {

    public AccountRegisterPageBean() {
    }

    @Inject
    private AccountController accountController;

    private final AccountDTO account = new AccountDTO();

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

    public String saveDataForRegistration() {
        if (!(repeatPassword.equals(account.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("registrationForm:repeatPassword", "passwords.not.matching");
            return null;
        }

        return accountController.confirmAccountForRegistration(account);
    }

}
