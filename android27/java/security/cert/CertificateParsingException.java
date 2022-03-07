package java.security.cert;

public class CertificateParsingException extends CertificateException
{
    private static final long serialVersionUID = -7989222416793322029L;
    
    public CertificateParsingException() {
    }
    
    public CertificateParsingException(final String s) {
        super(s);
    }
    
    public CertificateParsingException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CertificateParsingException(final Throwable t) {
        super(t);
    }
}
