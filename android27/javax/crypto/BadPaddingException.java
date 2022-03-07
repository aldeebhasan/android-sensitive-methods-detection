package javax.crypto;

import java.security.*;

public class BadPaddingException extends GeneralSecurityException
{
    private static final long serialVersionUID = -5315033893984728443L;
    
    public BadPaddingException() {
    }
    
    public BadPaddingException(final String s) {
        super(s);
    }
}
