package pl.lodz.p.it.spjava.e12.appstore.web.account;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.appstore.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.appstore.exception.AccountException;
import pl.lodz.p.it.spjava.e12.appstore.exception.AppBaseException;
import pl.lodz.p.it.spjava.e12.appstore.utils.ContextUtils;

@Named(value = "accountController")
@SessionScoped
public class AccountController implements Serializable {

    @Inject
    private AccountEndpoint accountEndpoint;

    private AccountDTO accountForRegistration;

    private AccountDTO accountForCreateByAdmin;

    private AccountDTO accountToEdit;

    public AccountDTO getAccountForRegistration() {
        return accountForRegistration;
    }

    public AccountDTO getAccountForCreateByAdmin() {
        return accountForCreateByAdmin;
    }

    public AccountDTO getAccountToEdit() {
        return accountToEdit;
    }

    /*
     * Rozpoczyna proces dodawania rekordu konta - ze scieżki rejestracji
     */
    public String registerAccount() {
        try {
            accountEndpoint.registerAccount(accountForRegistration);
            accountForRegistration = null;
            return "registrationSuccessful";
        } catch (AccountException ae) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji registerAccount wyjatku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji registerAccount wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    /*
     * Potwierdzenie dodania nowego rekordu aplikacji - ze scieżki rejestracji
     */
    public String confirmAccountForRegistration(AccountDTO account) {
        this.accountForRegistration = account;
        return "registrationConfirm";
    }

    /*
     * Rozpoczyna proces dodawania rekordu konta - ze scieżki tworzenia konta przez Admina
     */
    public String createNewAccountByAdmin() {
        try {
            accountEndpoint.createNewAccountByAdmin(accountForCreateByAdmin);
            accountForCreateByAdmin = null;
            return "registrationSuccessful";
        } catch (AccountException ae) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji createNewAccountByAdmin wyjatku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji createNewAccountByAdmin wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    /*
     * Potwierdzenie dodania nowego rekordu aplikacji - ze scieżki tworzenia konta przez Admina
     */
    public String confirmDataForCreateNewAccountByAdmin(AccountDTO account) {
        this.accountForCreateByAdmin = account;
        return "accountsCreateNewConfirm";
    }

    public List<AccountDTO> getAllAccounts() {
        return accountEndpoint.getAllAccounts();
    }

    public AccountDTO getMyAccount() {
        return accountEndpoint.getMyAccount();
    }

    public List<String> getAccountTypeList() {
        return accountEndpoint.getAccountTypeList();
    }

    /*
     * Wybiera rekord konta do edycji
     */
    public String selectAccountToEdit(AccountDTO accountDTO) {
        accountToEdit = accountEndpoint.findAccountToEdit(accountDTO);
        return "accountsEdit";
    }

    /*
     * Rozpoczyna proces zapisywania rekordu konta po edycji
     */
    public String saveAccountAfterEdit(AccountDTO accountDTO) {
        accountEndpoint.saveAccountAfterEdit(accountDTO);
        return "accountsEditSuccessful";
    }

    /*
     * Rozpoczyna proces usuwania rekordu konta
     */
    public String selectAccountToDelete(AccountDTO accountDTO) {
        try {
            accountEndpoint.deleteAccount(accountDTO);
            return "accountsDeleteSuccessful";
        } catch (AccountException ae) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji selectAccountToDelete wyjatku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji selectAccountToDelete wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public AccountController() {
    }

}
