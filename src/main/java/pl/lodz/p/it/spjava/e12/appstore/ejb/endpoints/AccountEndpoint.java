package pl.lodz.p.it.spjava.e12.appstore.ejb.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e12.appstore.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.appstore.ejb.interceptors.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.appstore.ejb.managers.AccountManager;
import pl.lodz.p.it.spjava.e12.appstore.exception.AccountException;
import pl.lodz.p.it.spjava.e12.appstore.exception.AppBaseException;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;
import pl.lodz.p.it.spjava.e12.appstore.model.Account.AccountType;
import pl.lodz.p.it.spjava.e12.appstore.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
public class AccountEndpoint {

    @Inject
    private AccountManager accountManager;

    private Account accountStatus;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    /*
     * Zwraca liste kont
     */
    @PermitAll
    public List<AccountDTO> getAllAccounts() {
        return DTOConverter.createListAccountsDTOfromEntity(accountManager.getAllAccounts());
    }

    /*
     * Zwraca dane kont dla zalogowanego usera
     */
    @RolesAllowed({"User", "Moderator", "Administrator"})
    public AccountDTO getMyAccount() {
        return DTOConverter.createAccountDTOfromEntity(accountManager.getMyAccount());
    }

    /*
     * Zwraca liste dostepnych ról
     */
    @PermitAll
    public List<String> getAccountTypeList() {
        ArrayList<String> accountTypeList = new ArrayList<>();
        for (AccountType type : AccountType.values()) {
            accountTypeList.add(type.toString());
        }
        return accountTypeList;
    }

    /*
     * Konwertuje obiekt AccountType do Enum
     */
    @PermitAll
    public AccountType convertAccountTypeToEnum(String typeString) {
        return AccountType.valueOf(typeString);
    }

    /*
     * Rejestracja nowego klienta z formularza rejestracji
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void registerAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = new Account();
        setDataToNewAccount(accountDTO, account);
        account.setAccountType(AccountType.USER); // tylko dla kont ze ściezki rejestracji -> zawsze USER

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.registerNewAccount(account);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

    /*
     * Rejestracja nowego klienta z formularza tworzenia usera przez Admina
     */
    @RolesAllowed({"Administrator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createNewAccountByAdmin(AccountDTO accountDTO) throws AppBaseException {
        Account account = new Account();
        setDataToNewAccount(accountDTO, account);
        account.setAccountType(convertAccountTypeToEnum(accountDTO.getAccountType())); // tylko dla kont ze ściezki tworzenia konta przez Admina, ustala rolę

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.registerNewAccount(account);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

    /*
     * Dostarcza dane z DTO do encji
     */
    private void setDataToNewAccount(AccountDTO accountDTO, Account account) {
        account.setLogin(accountDTO.getLogin());
        rewriteDataForNewAccountDTOtoEntity(accountDTO, account);
        account.setPassword(accountDTO.getPassword());
    }

    /*
     * Dostarcza dane z DTO do encji
     */
    private static void rewriteDataForNewAccountDTOtoEntity(AccountDTO accountDTO, Account account) {
        account.setName(accountDTO.getName());
        account.setSurname(accountDTO.getSurname());
        account.setEmail(accountDTO.getEmail());
    }

    /*
     * Usuwa rekord konta
     */
    @RolesAllowed({"Administrator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void deleteAccount(AccountDTO accountDTO) throws AppBaseException {

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.deleteAccount(accountDTO);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.deleteAccountExceptionWithTxRetryRollback();
        }

    }

    /*
     * Wyszukuje rekord konta do edycji
     */
    public AccountDTO findAccountToEdit(AccountDTO accountDTO) {
        accountStatus = accountManager.findAccountToEdit(accountDTO);
        return DTOConverter.createAccountDTOfromEntity(accountStatus);
    }

    /*
     * Zapisuje zmieniony rekord konta
     */
    public void saveAccountAfterEdit(AccountDTO accountDTO) {
        if (null == accountStatus) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        accountStatus.setAccountType(convertAccountTypeToEnum(accountDTO.getAccountType()));
        accountManager.editAccount(accountDTO, accountStatus);
        accountStatus = null;
    }

}
