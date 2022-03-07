package java.security.cert;

public class CertificateEncodingException extends CertificateException
{
    private static final long serialVersionUID = 6219492851589449162L;
    
    public CertificateEncodingException() {
    }
    
    public CertificateEncodingException(final String s) {
        super(s);
    }
    
    public CertificateEncodingException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CertificateEncodingException(final Throwable t) {
        super(t);
    }
}
