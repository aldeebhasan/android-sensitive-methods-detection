package java.security.cert;

import java.security.*;

public class CertificateException extends GeneralSecurityException
{
    private static final long serialVersionUID = 3192535253797119798L;
    
    public CertificateException() {
    }
    
    public CertificateException(final String s) {
        super(s);
    }
    
    public CertificateException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CertificateException(final Throwable t) {
        super(t);
    }
}
