package javax.crypto;

import java.security.*;

public class ShortBufferException extends GeneralSecurityException
{
    private static final long serialVersionUID = 8427718640832943747L;
    
    public ShortBufferException() {
    }
    
    public ShortBufferException(final String s) {
        super(s);
    }
}
