package javax.crypto;

import java.security.*;

public class IllegalBlockSizeException extends GeneralSecurityException
{
    private static final long serialVersionUID = -1965144811953540392L;
    
    public IllegalBlockSizeException() {
    }
    
    public IllegalBlockSizeException(final String s) {
        super(s);
    }
}
