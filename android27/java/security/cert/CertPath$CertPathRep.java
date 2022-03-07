package java.security.cert;

import java.io.*;

protected static class CertPathRep implements Serializable
{
    private static final long serialVersionUID = 3015633072427920915L;
    private String type;
    private byte[] data;
    
    protected CertPathRep(final String type, final byte[] data) {
        this.type = type;
        this.data = data;
    }
    
    protected Object readResolve() throws ObjectStreamException {
        try {
            return CertificateFactory.getInstance(this.type).generateCertPath(new ByteArrayInputStream(this.data));
        }
        catch (CertificateException ex2) {
            final NotSerializableException ex = new NotSerializableException("java.security.cert.CertPath: " + this.type);
            ex.initCause(ex2);
            throw ex;
        }
    }
}
