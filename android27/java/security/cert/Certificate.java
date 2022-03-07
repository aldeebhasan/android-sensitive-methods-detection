package java.security.cert;

import sun.security.x509.*;
import java.util.*;
import java.security.*;
import java.io.*;

public abstract class Certificate implements Serializable
{
    private static final long serialVersionUID = -3585440601605666277L;
    private final String type;
    private int hash;
    
    protected Certificate(final String type) {
        this.hash = -1;
        this.type = type;
    }
    
    public final String getType() {
        return this.type;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Certificate)) {
            return false;
        }
        try {
            return Arrays.equals(X509CertImpl.getEncodedInternal(this), X509CertImpl.getEncodedInternal((Certificate)o));
        }
        catch (CertificateException ex) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = this.hash;
        if (hash == -1) {
            try {
                hash = Arrays.hashCode(X509CertImpl.getEncodedInternal(this));
            }
            catch (CertificateException ex) {
                hash = 0;
            }
            this.hash = hash;
        }
        return hash;
    }
    
    public abstract byte[] getEncoded() throws CertificateEncodingException;
    
    public abstract void verify(final PublicKey p0) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
    
    public abstract void verify(final PublicKey p0, final String p1) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
    
    public void verify(final PublicKey publicKey, final Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public abstract String toString();
    
    public abstract PublicKey getPublicKey();
    
    protected Object writeReplace() throws ObjectStreamException {
        try {
            return new CertificateRep(this.type, this.getEncoded());
        }
        catch (CertificateException ex) {
            throw new NotSerializableException("java.security.cert.Certificate: " + this.type + ": " + ex.getMessage());
        }
    }
    
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
}
