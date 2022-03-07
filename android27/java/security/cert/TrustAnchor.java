package java.security.cert;

import java.security.*;
import javax.security.auth.x500.*;
import sun.security.x509.*;
import java.io.*;

public class TrustAnchor
{
    private final PublicKey pubKey;
    private final String caName;
    private final X500Principal caPrincipal;
    private final X509Certificate trustedCert;
    private byte[] ncBytes;
    private NameConstraintsExtension nc;
    
    public TrustAnchor(final X509Certificate trustedCert, final byte[] nameConstraints) {
        if (trustedCert == null) {
            throw new NullPointerException("the trustedCert parameter must be non-null");
        }
        this.trustedCert = trustedCert;
        this.pubKey = null;
        this.caName = null;
        this.caPrincipal = null;
        this.setNameConstraints(nameConstraints);
    }
    
    public TrustAnchor(final X500Principal caPrincipal, final PublicKey pubKey, final byte[] nameConstraints) {
        if (caPrincipal == null || pubKey == null) {
            throw new NullPointerException();
        }
        this.trustedCert = null;
        this.caPrincipal = caPrincipal;
        this.caName = caPrincipal.getName();
        this.pubKey = pubKey;
        this.setNameConstraints(nameConstraints);
    }
    
    public TrustAnchor(final String caName, final PublicKey pubKey, final byte[] nameConstraints) {
        if (pubKey == null) {
            throw new NullPointerException("the pubKey parameter must be non-null");
        }
        if (caName == null) {
            throw new NullPointerException("the caName parameter must be non-null");
        }
        if (caName.length() == 0) {
            throw new IllegalArgumentException("the caName parameter must be a non-empty String");
        }
        this.caPrincipal = new X500Principal(caName);
        this.pubKey = pubKey;
        this.caName = caName;
        this.trustedCert = null;
        this.setNameConstraints(nameConstraints);
    }
    
    public final X509Certificate getTrustedCert() {
        return this.trustedCert;
    }
    
    public final X500Principal getCA() {
        return this.caPrincipal;
    }
    
    public final String getCAName() {
        return this.caName;
    }
    
    public final PublicKey getCAPublicKey() {
        return this.pubKey;
    }
    
    private void setNameConstraints(final byte[] array) {
        if (array == null) {
            this.ncBytes = null;
            this.nc = null;
        }
        else {
            this.ncBytes = array.clone();
            try {
                this.nc = new NameConstraintsExtension(Boolean.FALSE, array);
            }
            catch (IOException ex2) {
                final IllegalArgumentException ex = new IllegalArgumentException(ex2.getMessage());
                ex.initCause(ex2);
                throw ex;
            }
        }
    }
    
    public final byte[] getNameConstraints() {
        return (byte[])((this.ncBytes == null) ? null : ((byte[])this.ncBytes.clone()));
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        if (this.pubKey != null) {
            sb.append("  Trusted CA Public Key: " + this.pubKey.toString() + "\n");
            sb.append("  Trusted CA Issuer Name: " + String.valueOf(this.caName) + "\n");
        }
        else {
            sb.append("  Trusted CA cert: " + this.trustedCert.toString() + "\n");
        }
        if (this.nc != null) {
            sb.append("  Name Constraints: " + this.nc.toString() + "\n");
        }
        return sb.toString();
    }
}
