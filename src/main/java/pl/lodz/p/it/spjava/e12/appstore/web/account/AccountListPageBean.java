package pl.lodz.p.it.spjava.e12.appstore.web.account;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;

@Named(value = "accountListPageBean")
@ViewScoped
public class AccountListPageBean implements Serializable {

    public AccountListPageBean() {
    }

    @Inject
    private AccountController accountController;

    private List<AccountDTO> accounts;

    private List<String> accountTypeList;

    @PostConstruct
    private void initModel() {
        accounts = accountController.getAllAccounts();
        accountTypeList = accountController.getAccountTypeList();
    }

    public List<AccountDTO> getAllAccounts() {
        return accounts;
    }

    public List<String> getAccountTypeList() {
        return accountTypeList;
    }

    public void refresh() {
        initModel();
    }

    public String editAccount(AccountDTO account) {
        return accountController.selectAccountToEdit(account);
    }

    public String deleteAccount(AccountDTO account) {
        return accountController.selectAccountToDelete(account);
    }

}
