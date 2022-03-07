package java.security.cert;

import sun.security.jca.*;
import java.security.*;

public class CertPathBuilder
{
    private static final String CPB_TYPE = "certpathbuilder.type";
    private final CertPathBuilderSpi builderSpi;
    private final Provider provider;
    private final String algorithm;
    
    protected CertPathBuilder(final CertPathBuilderSpi builderSpi, final Provider provider, final String algorithm) {
        this.builderSpi = builderSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    public static CertPathBuilder getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, s);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl, instance.provider, s);
    }
    
    public static CertPathBuilder getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, s, s2);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl, instance.provider, s);
    }
    
    public static CertPathBuilder getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, s, provider);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public final CertPathBuilderResult build(final CertPathParameters certPathParameters) throws CertPathBuilderException, InvalidAlgorithmParameterException {
        return this.builderSpi.engineBuild(certPathParameters);
    }
    
    public static final String getDefaultType() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("certpathbuilder.type");
            }
        });
        return (s == null) ? "PKIX" : s;
    }
    
    public final CertPathChecker getRevocationChecker() {
        return this.builderSpi.engineGetRevocationChecker();
    }
}
