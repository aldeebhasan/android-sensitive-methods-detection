package java.security.cert;

import java.util.*;
import sun.security.jca.*;
import java.security.*;

public class CertStore
{
    private static final String CERTSTORE_TYPE = "certstore.type";
    private CertStoreSpi storeSpi;
    private Provider provider;
    private String type;
    private CertStoreParameters params;
    
    protected CertStore(final CertStoreSpi storeSpi, final Provider provider, final String type, final CertStoreParameters certStoreParameters) {
        this.storeSpi = storeSpi;
        this.provider = provider;
        this.type = type;
        if (certStoreParameters != null) {
            this.params = (CertStoreParameters)certStoreParameters.clone();
        }
    }
    
    public final Collection<? extends Certificate> getCertificates(final CertSelector certSelector) throws CertStoreException {
        return this.storeSpi.engineGetCertificates(certSelector);
    }
    
    public final Collection<? extends CRL> getCRLs(final CRLSelector crlSelector) throws CertStoreException {
        return this.storeSpi.engineGetCRLs(crlSelector);
    }
    
    public static CertStore getInstance(final String s, final CertStoreParameters certStoreParameters) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, s, certStoreParameters);
            return new CertStore((CertStoreSpi)instance.impl, instance.provider, s, certStoreParameters);
        }
        catch (NoSuchAlgorithmException ex) {
            return handleException(ex);
        }
    }
    
    private static CertStore handleException(final NoSuchAlgorithmException ex) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        final Throwable cause = ex.getCause();
        if (cause instanceof InvalidAlgorithmParameterException) {
            throw (InvalidAlgorithmParameterException)cause;
        }
        throw ex;
    }
    
    public static CertStore getInstance(final String s, final CertStoreParameters certStoreParameters, final String s2) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, s, certStoreParameters, s2);
            return new CertStore((CertStoreSpi)instance.impl, instance.provider, s, certStoreParameters);
        }
        catch (NoSuchAlgorithmException ex) {
            return handleException(ex);
        }
    }
    
    public static CertStore getInstance(final String s, final CertStoreParameters certStoreParameters, final Provider provider) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, s, certStoreParameters, provider);
            return new CertStore((CertStoreSpi)instance.impl, instance.provider, s, certStoreParameters);
        }
        catch (NoSuchAlgorithmException ex) {
            return handleException(ex);
        }
    }
    
    public final CertStoreParameters getCertStoreParameters() {
        return (this.params == null) ? null : ((CertStoreParameters)this.params.clone());
    }
    
    public final String getType() {
        return this.type;
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public static final String getDefaultType() {
        String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("certstore.type");
            }
        });
        if (s == null) {
            s = "LDAP";
        }
        return s;
    }
}
