package javax.crypto;

import java.security.*;

public class NoSuchPaddingException extends GeneralSecurityException
{
    private static final long serialVersionUID = -4572885201200175466L;
    
    public NoSuchPaddingException() {
    }
    
    public NoSuchPaddingException(final String s) {
        super(s);
    }
}
