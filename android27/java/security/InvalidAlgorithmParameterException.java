package java.security;

public class InvalidAlgorithmParameterException extends GeneralSecurityException
{
    private static final long serialVersionUID = 2864672297499471472L;
    
    public InvalidAlgorithmParameterException() {
    }
    
    public InvalidAlgorithmParameterException(final String s) {
        super(s);
    }
    
    public InvalidAlgorithmParameterException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public InvalidAlgorithmParameterException(final Throwable t) {
        super(t);
    }
}
