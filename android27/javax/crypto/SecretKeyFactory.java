package javax.crypto;

import java.util.*;
import sun.security.jca.*;
import java.security.spec.*;
import java.security.*;

public class SecretKeyFactory
{
    private Provider provider;
    private final String algorithm;
    private volatile SecretKeyFactorySpi spi;
    private final Object lock;
    private Iterator<Provider.Service> serviceIterator;
    
    protected SecretKeyFactory(final SecretKeyFactorySpi spi, final Provider provider, final String algorithm) {
        this.lock = new Object();
        this.spi = spi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    private SecretKeyFactory(final String algorithm) throws NoSuchAlgorithmException {
        this.lock = new Object();
        this.algorithm = algorithm;
        this.serviceIterator = GetInstance.getServices("SecretKeyFactory", algorithm).iterator();
        if (this.nextSpi(null) == null) {
            throw new NoSuchAlgorithmException(algorithm + " SecretKeyFactory not available");
        }
    }
    
    public static final SecretKeyFactory getInstance(final String s) throws NoSuchAlgorithmException {
        return new SecretKeyFactory(s);
    }
    
    public static final SecretKeyFactory getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = JceSecurity.getInstance("SecretKeyFactory", SecretKeyFactorySpi.class, s, s2);
        return new SecretKeyFactory((SecretKeyFactorySpi)instance.impl, instance.provider, s);
    }
    
    public static final SecretKeyFactory getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = JceSecurity.getInstance("SecretKeyFactory", SecretKeyFactorySpi.class, s, provider);
        return new SecretKeyFactory((SecretKeyFactorySpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        synchronized (this.lock) {
            this.serviceIterator = null;
            return this.provider;
        }
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    private SecretKeyFactorySpi nextSpi(final SecretKeyFactorySpi secretKeyFactorySpi) {
        synchronized (this.lock) {
            if (secretKeyFactorySpi != null && secretKeyFactorySpi != this.spi) {
                return this.spi;
            }
            if (this.serviceIterator == null) {
                return null;
            }
            while (this.serviceIterator.hasNext()) {
                final Provider.Service service = this.serviceIterator.next();
                if (!JceSecurity.canUseProvider(service.getProvider())) {
                    continue;
                }
                try {
                    final Object instance = service.newInstance(null);
                    if (!(instance instanceof SecretKeyFactorySpi)) {
                        continue;
                    }
                    final SecretKeyFactorySpi spi = (SecretKeyFactorySpi)instance;
                    this.provider = service.getProvider();
                    return this.spi = spi;
                }
                catch (NoSuchAlgorithmException ex) {
                    continue;
                }
                break;
            }
            this.serviceIterator = null;
            return null;
        }
    }
    
    public final SecretKey generateSecret(final KeySpec keySpec) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGenerateSecret(keySpec);
        }
        Throwable t = null;
        SecretKeyFactorySpi secretKeyFactorySpi = this.spi;
        try {
            return secretKeyFactorySpi.engineGenerateSecret(keySpec);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            secretKeyFactorySpi = this.nextSpi(secretKeyFactorySpi);
            if (secretKeyFactorySpi != null) {
                return secretKeyFactorySpi.engineGenerateSecret(keySpec);
            }
            if (t instanceof InvalidKeySpecException) {
                throw (InvalidKeySpecException)t;
            }
            throw new InvalidKeySpecException("Could not generate secret key", t);
        }
    }
    
    public final KeySpec getKeySpec(final SecretKey secretKey, final Class<?> clazz) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGetKeySpec(secretKey, clazz);
        }
        Throwable t = null;
        SecretKeyFactorySpi secretKeyFactorySpi = this.spi;
        try {
            return secretKeyFactorySpi.engineGetKeySpec(secretKey, clazz);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            secretKeyFactorySpi = this.nextSpi(secretKeyFactorySpi);
            if (secretKeyFactorySpi != null) {
                return secretKeyFactorySpi.engineGetKeySpec(secretKey, clazz);
            }
            if (t instanceof InvalidKeySpecException) {
                throw (InvalidKeySpecException)t;
            }
            throw new InvalidKeySpecException("Could not get key spec", t);
        }
    }
    
    public final SecretKey translateKey(final SecretKey secretKey) throws InvalidKeyException {
        if (this.serviceIterator == null) {
            return this.spi.engineTranslateKey(secretKey);
        }
        Throwable t = null;
        SecretKeyFactorySpi secretKeyFactorySpi = this.spi;
        try {
            return secretKeyFactorySpi.engineTranslateKey(secretKey);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            secretKeyFactorySpi = this.nextSpi(secretKeyFactorySpi);
            if (secretKeyFactorySpi != null) {
                return secretKeyFactorySpi.engineTranslateKey(secretKey);
            }
            if (t instanceof InvalidKeyException) {
                throw (InvalidKeyException)t;
            }
            throw new InvalidKeyException("Could not translate key", t);
        }
    }
}
