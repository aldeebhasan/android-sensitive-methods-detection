package java.security.spec;

import java.security.*;

public class InvalidParameterSpecException extends GeneralSecurityException
{
    private static final long serialVersionUID = -970468769593399342L;
    
    public InvalidParameterSpecException() {
    }
    
    public InvalidParameterSpecException(final String s) {
        super(s);
    }
}
