package javax.crypto;

import java.security.*;

public class ExemptionMechanismException extends GeneralSecurityException
{
    private static final long serialVersionUID = 1572699429277957109L;
    
    public ExemptionMechanismException() {
    }
    
    public ExemptionMechanismException(final String s) {
        super(s);
    }
}
