package java.sql;

public class SQLTransientConnectionException extends SQLTransientException
{
    private static final long serialVersionUID = -2520155553543391200L;
    
    public SQLTransientConnectionException() {
    }
    
    public SQLTransientConnectionException(final String s) {
        super(s);
    }
    
    public SQLTransientConnectionException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLTransientConnectionException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLTransientConnectionException(final Throwable t) {
        super(t);
    }
    
    public SQLTransientConnectionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLTransientConnectionException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLTransientConnectionException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
