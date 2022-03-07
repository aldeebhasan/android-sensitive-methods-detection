package java.security.cert;

import sun.security.jca.*;
import java.security.*;

public class CertPathValidator
{
    private static final String CPV_TYPE = "certpathvalidator.type";
    private final CertPathValidatorSpi validatorSpi;
    private final Provider provider;
    private final String algorithm;
    
    protected CertPathValidator(final CertPathValidatorSpi validatorSpi, final Provider provider, final String algorithm) {
        this.validatorSpi = validatorSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    public static CertPathValidator getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, s);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl, instance.provider, s);
    }
    
    public static CertPathValidator getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, s, s2);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl, instance.provider, s);
    }
    
    public static CertPathValidator getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, s, provider);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public final CertPathValidatorResult validate(final CertPath certPath, final CertPathParameters certPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        return this.validatorSpi.engineValidate(certPath, certPathParameters);
    }
    
    public static final String getDefaultType() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("certpathvalidator.type");
            }
        });
        return (s == null) ? "PKIX" : s;
    }
    
    public final CertPathChecker getRevocationChecker() {
        return this.validatorSpi.engineGetRevocationChecker();
    }
}
