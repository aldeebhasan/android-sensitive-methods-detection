package java.security.cert;

import java.io.*;

protected static class CertificateRep implements Serializable
{
    private static final long serialVersionUID = -8563758940495660020L;
    private String type;
    private byte[] data;
    
    protected CertificateRep(final String type, final byte[] data) {
        this.type = type;
        this.data = data;
    }
    
    protected Object readResolve() throws ObjectStreamException {
        try {
            return CertificateFactory.getInstance(this.type).generateCertificate(new ByteArrayInputStream(this.data));
        }
        catch (CertificateException ex) {
            throw new NotSerializableException("java.security.cert.Certificate: " + this.type + ": " + ex.getMessage());
        }
    }
}
