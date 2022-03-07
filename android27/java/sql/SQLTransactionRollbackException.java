package java.sql;

public class SQLTransactionRollbackException extends SQLTransientException
{
    private static final long serialVersionUID = 5246680841170837229L;
    
    public SQLTransactionRollbackException() {
    }
    
    public SQLTransactionRollbackException(final String s) {
        super(s);
    }
    
    public SQLTransactionRollbackException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLTransactionRollbackException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLTransactionRollbackException(final Throwable t) {
        super(t);
    }
    
    public SQLTransactionRollbackException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLTransactionRollbackException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLTransactionRollbackException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
