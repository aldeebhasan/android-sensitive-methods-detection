package java.security.cert;

import java.security.*;

public class CertStoreException extends GeneralSecurityException
{
    private static final long serialVersionUID = 2395296107471573245L;
    
    public CertStoreException() {
    }
    
    public CertStoreException(final String s) {
        super(s);
    }
    
    public CertStoreException(final Throwable t) {
        super(t);
    }
    
    public CertStoreException(final String s, final Throwable t) {
        super(s, t);
    }
}
