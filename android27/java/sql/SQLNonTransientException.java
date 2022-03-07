package java.sql;

public class SQLNonTransientException extends SQLException
{
    private static final long serialVersionUID = -9104382843534716547L;
    
    public SQLNonTransientException() {
    }
    
    public SQLNonTransientException(final String s) {
        super(s);
    }
    
    public SQLNonTransientException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLNonTransientException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLNonTransientException(final Throwable t) {
        super(t);
    }
    
    public SQLNonTransientException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLNonTransientException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLNonTransientException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
