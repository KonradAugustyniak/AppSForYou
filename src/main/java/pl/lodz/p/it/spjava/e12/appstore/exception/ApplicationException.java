package pl.lodz.p.it.spjava.e12.appstore.exception;

public class ApplicationException extends AppBaseException {

    static final public String KEY_DB_CONSTRAINT = "error.app.db.constraint";

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    static public ApplicationException uploadApplicationExceptionWithTxRetryRollback() {
        ApplicationException ae = new ApplicationException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

    static public ApplicationException deleteApplicationExceptionWithTxRetryRollback() {
        ApplicationException ae = new ApplicationException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

}
