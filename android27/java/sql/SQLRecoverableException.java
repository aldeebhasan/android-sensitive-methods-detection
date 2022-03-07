package java.sql;

public class SQLRecoverableException extends SQLException
{
    private static final long serialVersionUID = -4144386502923131579L;
    
    public SQLRecoverableException() {
    }
    
    public SQLRecoverableException(final String s) {
        super(s);
    }
    
    public SQLRecoverableException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLRecoverableException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLRecoverableException(final Throwable t) {
        super(t);
    }
    
    public SQLRecoverableException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLRecoverableException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLRecoverableException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
