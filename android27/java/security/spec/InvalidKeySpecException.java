package java.security.spec;

import java.security.*;

public class InvalidKeySpecException extends GeneralSecurityException
{
    private static final long serialVersionUID = 3546139293998810778L;
    
    public InvalidKeySpecException() {
    }
    
    public InvalidKeySpecException(final String s) {
        super(s);
    }
    
    public InvalidKeySpecException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public InvalidKeySpecException(final Throwable t) {
        super(t);
    }
}
