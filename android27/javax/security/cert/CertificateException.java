package javax.security.cert;

public class CertificateException extends Exception
{
    private static final long serialVersionUID = -5757213374030785290L;
    
    public CertificateException() {
    }
    
    public CertificateException(final String s) {
        super(s);
    }
}
