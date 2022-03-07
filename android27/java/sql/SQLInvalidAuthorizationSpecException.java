package java.sql;

public class SQLInvalidAuthorizationSpecException extends SQLNonTransientException
{
    private static final long serialVersionUID = -64105250450891498L;
    
    public SQLInvalidAuthorizationSpecException() {
    }
    
    public SQLInvalidAuthorizationSpecException(final String s) {
        super(s);
    }
    
    public SQLInvalidAuthorizationSpecException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLInvalidAuthorizationSpecException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLInvalidAuthorizationSpecException(final Throwable t) {
        super(t);
    }
    
    public SQLInvalidAuthorizationSpecException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLInvalidAuthorizationSpecException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLInvalidAuthorizationSpecException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
