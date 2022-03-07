package java.security.cert;

import javax.security.auth.x500.*;
import sun.security.x509.*;
import sun.security.util.*;
import java.security.*;
import java.math.*;
import java.util.*;

public abstract class X509CRL extends CRL implements X509Extension
{
    private transient X500Principal issuerPrincipal;
    
    protected X509CRL() {
        super("X.509");
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof X509CRL)) {
            return false;
        }
        try {
            return Arrays.equals(X509CRLImpl.getEncodedInternal(this), X509CRLImpl.getEncodedInternal((X509CRL)o));
        }
        catch (CRLException ex) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        byte b = 0;
        try {
            final byte[] encodedInternal = X509CRLImpl.getEncodedInternal(this);
            for (byte b2 = 1; b2 < encodedInternal.length; ++b2) {
                b += (byte)(encodedInternal[b2] * b2);
            }
            return b;
        }
        catch (CRLException ex) {
            return b;
        }
    }
    
    public abstract byte[] getEncoded() throws CRLException;
    
    public abstract void verify(final PublicKey p0) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
    
    public abstract void verify(final PublicKey p0, final String p1) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
    
    public void verify(final PublicKey publicKey, final Provider provider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final String sigAlgName = this.getSigAlgName();
        final Signature signature = (provider == null) ? Signature.getInstance(sigAlgName) : Signature.getInstance(sigAlgName, provider);
        try {
            SignatureUtil.initVerifyWithParam(signature, publicKey, SignatureUtil.getParamSpec(sigAlgName, this.getSigAlgParams()));
        }
        catch (ProviderException ex) {
            throw new CRLException(ex.getMessage(), ex.getCause());
        }
        catch (InvalidAlgorithmParameterException ex2) {
            throw new CRLException(ex2);
        }
        final byte[] tbsCertList = this.getTBSCertList();
        signature.update(tbsCertList, 0, tbsCertList.length);
        if (!signature.verify(this.getSignature())) {
            throw new SignatureException("Signature does not match.");
        }
    }
    
    public abstract int getVersion();
    
    public abstract Principal getIssuerDN();
    
    public X500Principal getIssuerX500Principal() {
        if (this.issuerPrincipal == null) {
            this.issuerPrincipal = X509CRLImpl.getIssuerX500Principal(this);
        }
        return this.issuerPrincipal;
    }
    
    public abstract Date getThisUpdate();
    
    public abstract Date getNextUpdate();
    
    public abstract X509CRLEntry getRevokedCertificate(final BigInteger p0);
    
    public X509CRLEntry getRevokedCertificate(final X509Certificate x509Certificate) {
        if (!x509Certificate.getIssuerX500Principal().equals(this.getIssuerX500Principal())) {
            return null;
        }
        return this.getRevokedCertificate(x509Certificate.getSerialNumber());
    }
    
    public abstract Set<? extends X509CRLEntry> getRevokedCertificates();
    
    public abstract byte[] getTBSCertList() throws CRLException;
    
    public abstract byte[] getSignature();
    
    public abstract String getSigAlgName();
    
    public abstract String getSigAlgOID();
    
    public abstract byte[] getSigAlgParams();
}
