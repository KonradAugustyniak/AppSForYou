package pl.lodz.p.it.spjava.e12.appstore.exception;

public class AccountException extends AppBaseException {

    static final public String KEY_DB_CONSTRAINT = "error.konto.db.constraint.uniq.login";

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountException notFoundLoggedInUserException() {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT);
        return ae;
    }

    static public AccountException createAccountExceptionWithTxRetryRollback() {
        AccountException ae = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

    static public AccountException deleteAccountExceptionWithTxRetryRollback() {
        AccountException ae = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

}
