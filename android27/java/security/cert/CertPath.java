package java.security.cert;

import java.util.*;
import java.io.*;

public abstract class CertPath implements Serializable
{
    private static final long serialVersionUID = 6068470306649138683L;
    private String type;
    
    protected CertPath(final String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    public abstract Iterator<String> getEncodings();
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertPath)) {
            return false;
        }
        final CertPath certPath = (CertPath)o;
        return certPath.getType().equals(this.type) && this.getCertificates().equals(certPath.getCertificates());
    }
    
    @Override
    public int hashCode() {
        return 31 * this.type.hashCode() + this.getCertificates().hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        final Iterator<? extends Certificate> iterator = this.getCertificates().iterator();
        sb.append("\n" + this.type + " Cert Path: length = " + this.getCertificates().size() + ".\n");
        sb.append("[\n");
        int n = 1;
        while (iterator.hasNext()) {
            sb.append("=========================================================Certificate " + n + " start.\n");
            sb.append(((Certificate)iterator.next()).toString());
            sb.append("\n=========================================================Certificate " + n + " end.\n\n\n");
            ++n;
        }
        sb.append("\n]");
        return sb.toString();
    }
    
    public abstract byte[] getEncoded() throws CertificateEncodingException;
    
    public abstract byte[] getEncoded(final String p0) throws CertificateEncodingException;
    
    public abstract List<? extends Certificate> getCertificates();
    
    protected Object writeReplace() throws ObjectStreamException {
        try {
            return new CertPathRep(this.type, this.getEncoded());
        }
        catch (CertificateException ex2) {
            final NotSerializableException ex = new NotSerializableException("java.security.cert.CertPath: " + this.type);
            ex.initCause(ex2);
            throw ex;
        }
    }
    
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
}
