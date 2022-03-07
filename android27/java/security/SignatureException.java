package java.security;

public class SignatureException extends GeneralSecurityException
{
    private static final long serialVersionUID = 7509989324975124438L;
    
    public SignatureException() {
    }
    
    public SignatureException(final String s) {
        super(s);
    }
    
    public SignatureException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SignatureException(final Throwable t) {
        super(t);
    }
}
