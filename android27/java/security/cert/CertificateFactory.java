package java.security.cert;

import sun.security.jca.*;
import java.security.*;
import java.io.*;
import java.util.*;

public class CertificateFactory
{
    private String type;
    private Provider provider;
    private CertificateFactorySpi certFacSpi;
    
    protected CertificateFactory(final CertificateFactorySpi certFacSpi, final Provider provider, final String type) {
        this.certFacSpi = certFacSpi;
        this.provider = provider;
        this.type = type;
    }
    
    public static final CertificateFactory getInstance(final String s) throws CertificateException {
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("CertificateFactory", CertificateFactorySpi.class, s);
            return new CertificateFactory((CertificateFactorySpi)instance.impl, instance.provider, s);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new CertificateException(s + " not found", ex);
        }
    }
    
    public static final CertificateFactory getInstance(final String s, final String s2) throws CertificateException, NoSuchProviderException {
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("CertificateFactory", CertificateFactorySpi.class, s, s2);
            return new CertificateFactory((CertificateFactorySpi)instance.impl, instance.provider, s);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new CertificateException(s + " not found", ex);
        }
    }
    
    public static final CertificateFactory getInstance(final String s, final Provider provider) throws CertificateException {
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("CertificateFactory", CertificateFactorySpi.class, s, provider);
            return new CertificateFactory((CertificateFactorySpi)instance.impl, instance.provider, s);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new CertificateException(s + " not found", ex);
        }
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final String getType() {
        return this.type;
    }
    
    public final Certificate generateCertificate(final InputStream inputStream) throws CertificateException {
        return this.certFacSpi.engineGenerateCertificate(inputStream);
    }
    
    public final Iterator<String> getCertPathEncodings() {
        return this.certFacSpi.engineGetCertPathEncodings();
    }
    
    public final CertPath generateCertPath(final InputStream inputStream) throws CertificateException {
        return this.certFacSpi.engineGenerateCertPath(inputStream);
    }
    
    public final CertPath generateCertPath(final InputStream inputStream, final String s) throws CertificateException {
        return this.certFacSpi.engineGenerateCertPath(inputStream, s);
    }
    
    public final CertPath generateCertPath(final List<? extends Certificate> list) throws CertificateException {
        return this.certFacSpi.engineGenerateCertPath(list);
    }
    
    public final Collection<? extends Certificate> generateCertificates(final InputStream inputStream) throws CertificateException {
        return this.certFacSpi.engineGenerateCertificates(inputStream);
    }
    
    public final CRL generateCRL(final InputStream inputStream) throws CRLException {
        return this.certFacSpi.engineGenerateCRL(inputStream);
    }
    
    public final Collection<? extends CRL> generateCRLs(final InputStream inputStream) throws CRLException {
        return this.certFacSpi.engineGenerateCRLs(inputStream);
    }
}
