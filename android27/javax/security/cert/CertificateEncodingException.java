package javax.security.cert;

public class CertificateEncodingException extends CertificateException
{
    private static final long serialVersionUID = -8187642723048403470L;
    
    public CertificateEncodingException() {
    }
    
    public CertificateEncodingException(final String s) {
        super(s);
    }
}
