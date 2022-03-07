package java.security;

import java.security.cert.*;
import java.io.*;

public final class CodeSigner implements Serializable
{
    private static final long serialVersionUID = 6819288105193937581L;
    private CertPath signerCertPath;
    private Timestamp timestamp;
    private transient int myhash;
    
    public CodeSigner(final CertPath signerCertPath, final Timestamp timestamp) {
        this.myhash = -1;
        if (signerCertPath == null) {
            throw new NullPointerException();
        }
        this.signerCertPath = signerCertPath;
        this.timestamp = timestamp;
    }
    
    public CertPath getSignerCertPath() {
        return this.signerCertPath;
    }
    
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    
    @Override
    public int hashCode() {
        if (this.myhash == -1) {
            if (this.timestamp == null) {
                this.myhash = this.signerCertPath.hashCode();
            }
            else {
                this.myhash = this.signerCertPath.hashCode() + this.timestamp.hashCode();
            }
        }
        return this.myhash;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof CodeSigner)) {
            return false;
        }
        final CodeSigner codeSigner = (CodeSigner)o;
        if (this == codeSigner) {
            return true;
        }
        final Timestamp timestamp = codeSigner.getTimestamp();
        if (this.timestamp == null) {
            if (timestamp != null) {
                return false;
            }
        }
        else if (timestamp == null || !this.timestamp.equals(timestamp)) {
            return false;
        }
        return this.signerCertPath.equals(codeSigner.getSignerCertPath());
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append("Signer: " + this.signerCertPath.getCertificates().get(0));
        if (this.timestamp != null) {
            sb.append("timestamp: " + this.timestamp);
        }
        sb.append(")");
        return sb.toString();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.myhash = -1;
    }
}
