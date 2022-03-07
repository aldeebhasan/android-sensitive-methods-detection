package java.security;

public class GeneralSecurityException extends Exception
{
    private static final long serialVersionUID = 894798122053539237L;
    
    public GeneralSecurityException() {
    }
    
    public GeneralSecurityException(final String s) {
        super(s);
    }
    
    public GeneralSecurityException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public GeneralSecurityException(final Throwable t) {
        super(t);
    }
}
