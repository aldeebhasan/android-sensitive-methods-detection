package java.security.cert;

import java.security.*;

public class CertPathBuilderException extends GeneralSecurityException
{
    private static final long serialVersionUID = 5316471420178794402L;
    
    public CertPathBuilderException() {
    }
    
    public CertPathBuilderException(final String s) {
        super(s);
    }
    
    public CertPathBuilderException(final Throwable t) {
        super(t);
    }
    
    public CertPathBuilderException(final String s, final Throwable t) {
        super(s, t);
    }
}
