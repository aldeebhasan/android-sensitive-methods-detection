package java.security.cert;

import javax.security.auth.x500.*;
import java.math.*;
import sun.security.x509.*;
import java.util.*;
import sun.security.util.*;
import java.security.*;

public abstract class X509Certificate extends Certificate implements X509Extension
{
    private static final long serialVersionUID = -2491127588187038216L;
    private transient X500Principal subjectX500Principal;
    private transient X500Principal issuerX500Principal;
    
    protected X509Certificate() {
        super("X.509");
    }
    
    public abstract void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;
    
    public abstract void checkValidity(final Date p0) throws CertificateExpiredException, CertificateNotYetValidException;
    
    public abstract int getVersion();
    
    public abstract BigInteger getSerialNumber();
    
    public abstract Principal getIssuerDN();
    
    public X500Principal getIssuerX500Principal() {
        if (this.issuerX500Principal == null) {
            this.issuerX500Principal = X509CertImpl.getIssuerX500Principal(this);
        }
        return this.issuerX500Principal;
    }
    
    public abstract Principal getSubjectDN();
    
    public X500Principal getSubjectX500Principal() {
        if (this.subjectX500Principal == null) {
            this.subjectX500Principal = X509CertImpl.getSubjectX500Principal(this);
        }
        return this.subjectX500Principal;
    }
    
    public abstract Date getNotBefore();
    
    public abstract Date getNotAfter();
    
    public abstract byte[] getTBSCertificate() throws CertificateEncodingException;
    
    public abstract byte[] getSignature();
    
    public abstract String getSigAlgName();
    
    public abstract String getSigAlgOID();
    
    public abstract byte[] getSigAlgParams();
    
    public abstract boolean[] getIssuerUniqueID();
    
    public abstract boolean[] getSubjectUniqueID();
    
    public abstract boolean[] getKeyUsage();
    
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return X509CertImpl.getExtendedKeyUsage(this);
    }
    
    public abstract int getBasicConstraints();
    
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return X509CertImpl.getSubjectAlternativeNames(this);
    }
    
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return X509CertImpl.getIssuerAlternativeNames(this);
    }
    
    @Override
    public void verify(final PublicKey publicKey, final Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final String sigAlgName = this.getSigAlgName();
        final Signature signature = (provider == null) ? Signature.getInstance(sigAlgName) : Signature.getInstance(sigAlgName, provider);
        try {
            SignatureUtil.initVerifyWithParam(signature, publicKey, SignatureUtil.getParamSpec(sigAlgName, this.getSigAlgParams()));
        }
        catch (ProviderException ex) {
            throw new CertificateException(ex.getMessage(), ex.getCause());
        }
        catch (InvalidAlgorithmParameterException ex2) {
            throw new CertificateException(ex2);
        }
        final byte[] tbsCertificate = this.getTBSCertificate();
        signature.update(tbsCertificate, 0, tbsCertificate.length);
        if (!signature.verify(this.getSignature())) {
            throw new SignatureException("Signature does not match.");
        }
    }
}
