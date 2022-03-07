package java.security.cert;

import java.security.*;

public class CRLException extends GeneralSecurityException
{
    private static final long serialVersionUID = -6694728944094197147L;
    
    public CRLException() {
    }
    
    public CRLException(final String s) {
        super(s);
    }
    
    public CRLException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CRLException(final Throwable t) {
        super(t);
    }
}
