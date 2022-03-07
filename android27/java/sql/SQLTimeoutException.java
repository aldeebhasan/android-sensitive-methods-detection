package java.sql;

public class SQLTimeoutException extends SQLTransientException
{
    private static final long serialVersionUID = -4487171280562520262L;
    
    public SQLTimeoutException() {
    }
    
    public SQLTimeoutException(final String s) {
        super(s);
    }
    
    public SQLTimeoutException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLTimeoutException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLTimeoutException(final Throwable t) {
        super(t);
    }
    
    public SQLTimeoutException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLTimeoutException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLTimeoutException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
