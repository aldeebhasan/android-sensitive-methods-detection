package java.sql;

public class SQLNonTransientConnectionException extends SQLNonTransientException
{
    private static final long serialVersionUID = -5852318857474782892L;
    
    public SQLNonTransientConnectionException() {
    }
    
    public SQLNonTransientConnectionException(final String s) {
        super(s);
    }
    
    public SQLNonTransientConnectionException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLNonTransientConnectionException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLNonTransientConnectionException(final Throwable t) {
        super(t);
    }
    
    public SQLNonTransientConnectionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLNonTransientConnectionException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLNonTransientConnectionException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
