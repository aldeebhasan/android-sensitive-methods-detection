package javax.security.cert;

public class CertificateExpiredException extends CertificateException
{
    private static final long serialVersionUID = 5091601212177261883L;
    
    public CertificateExpiredException() {
    }
    
    public CertificateExpiredException(final String s) {
        super(s);
    }
}
