package javax.net.ssl;

import sun.security.jca.*;
import java.security.*;

public class KeyManagerFactory
{
    private Provider provider;
    private KeyManagerFactorySpi factorySpi;
    private String algorithm;
    
    public static final String getDefaultAlgorithm() {
        String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("ssl.KeyManagerFactory.algorithm");
            }
        });
        if (s == null) {
            s = "SunX509";
        }
        return s;
    }
    
    protected KeyManagerFactory(final KeyManagerFactorySpi factorySpi, final Provider provider, final String algorithm) {
        this.factorySpi = factorySpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static final KeyManagerFactory getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("KeyManagerFactory", KeyManagerFactorySpi.class, s);
        return new KeyManagerFactory((KeyManagerFactorySpi)instance.impl, instance.provider, s);
    }
    
    public static final KeyManagerFactory getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("KeyManagerFactory", KeyManagerFactorySpi.class, s, s2);
        return new KeyManagerFactory((KeyManagerFactorySpi)instance.impl, instance.provider, s);
    }
    
    public static final KeyManagerFactory getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("KeyManagerFactory", KeyManagerFactorySpi.class, s, provider);
        return new KeyManagerFactory((KeyManagerFactorySpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final void init(final KeyStore keyStore, final char[] array) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        this.factorySpi.engineInit(keyStore, array);
    }
    
    public final void init(final ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        this.factorySpi.engineInit(managerFactoryParameters);
    }
    
    public final KeyManager[] getKeyManagers() {
        return this.factorySpi.engineGetKeyManagers();
    }
}
