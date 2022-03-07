package java.security;

public class DigestException extends GeneralSecurityException
{
    private static final long serialVersionUID = 5821450303093652515L;
    
    public DigestException() {
    }
    
    public DigestException(final String s) {
        super(s);
    }
    
    public DigestException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public DigestException(final Throwable t) {
        super(t);
    }
}
