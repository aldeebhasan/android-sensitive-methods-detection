package java.security;

public class KeyException extends GeneralSecurityException
{
    private static final long serialVersionUID = -7483676942812432108L;
    
    public KeyException() {
    }
    
    public KeyException(final String s) {
        super(s);
    }
    
    public KeyException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public KeyException(final Throwable t) {
        super(t);
    }
}
