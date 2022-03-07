package java.security;

public class NoSuchAlgorithmException extends GeneralSecurityException
{
    private static final long serialVersionUID = -7443947487218346562L;
    
    public NoSuchAlgorithmException() {
    }
    
    public NoSuchAlgorithmException(final String s) {
        super(s);
    }
    
    public NoSuchAlgorithmException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public NoSuchAlgorithmException(final Throwable t) {
        super(t);
    }
}
