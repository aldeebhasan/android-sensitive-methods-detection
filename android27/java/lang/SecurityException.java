package java.lang;

public class SecurityException extends RuntimeException
{
    private static final long serialVersionUID = 6878364983674394167L;
    
    public SecurityException() {
    }
    
    public SecurityException(final String s) {
        super(s);
    }
    
    public SecurityException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SecurityException(final Throwable t) {
        super(t);
    }
}
