package java.sql;

public class SQLTransientException extends SQLException
{
    private static final long serialVersionUID = -9042733978262274539L;
    
    public SQLTransientException() {
    }
    
    public SQLTransientException(final String s) {
        super(s);
    }
    
    public SQLTransientException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLTransientException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLTransientException(final Throwable t) {
        super(t);
    }
    
    public SQLTransientException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLTransientException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLTransientException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
