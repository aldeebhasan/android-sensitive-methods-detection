package javax.net.ssl;

import sun.security.jca.*;
import java.security.*;

public class TrustManagerFactory
{
    private Provider provider;
    private TrustManagerFactorySpi factorySpi;
    private String algorithm;
    
    public static final String getDefaultAlgorithm() {
        String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("ssl.TrustManagerFactory.algorithm");
            }
        });
        if (s == null) {
            s = "SunX509";
        }
        return s;
    }
    
    protected TrustManagerFactory(final TrustManagerFactorySpi factorySpi, final Provider provider, final String algorithm) {
        this.factorySpi = factorySpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static final TrustManagerFactory getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("TrustManagerFactory", TrustManagerFactorySpi.class, s);
        return new TrustManagerFactory((TrustManagerFactorySpi)instance.impl, instance.provider, s);
    }
    
    public static final TrustManagerFactory getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("TrustManagerFactory", TrustManagerFactorySpi.class, s, s2);
        return new TrustManagerFactory((TrustManagerFactorySpi)instance.impl, instance.provider, s);
    }
    
    public static final TrustManagerFactory getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("TrustManagerFactory", TrustManagerFactorySpi.class, s, provider);
        return new TrustManagerFactory((TrustManagerFactorySpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final void init(final KeyStore keyStore) throws KeyStoreException {
        this.factorySpi.engineInit(keyStore);
    }
    
    public final void init(final ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        this.factorySpi.engineInit(managerFactoryParameters);
    }
    
    public final TrustManager[] getTrustManagers() {
        return this.factorySpi.engineGetTrustManagers();
    }
}
