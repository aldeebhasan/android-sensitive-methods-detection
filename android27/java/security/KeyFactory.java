package java.security;

import sun.security.util.*;
import java.util.*;
import sun.security.jca.*;
import java.security.spec.*;

public class KeyFactory
{
    private static final Debug debug;
    private final String algorithm;
    private Provider provider;
    private volatile KeyFactorySpi spi;
    private final Object lock;
    private Iterator<Provider.Service> serviceIterator;
    
    protected KeyFactory(final KeyFactorySpi spi, final Provider provider, final String algorithm) {
        this.lock = new Object();
        this.spi = spi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    private KeyFactory(final String algorithm) throws NoSuchAlgorithmException {
        this.lock = new Object();
        this.algorithm = algorithm;
        this.serviceIterator = GetInstance.getServices("KeyFactory", algorithm).iterator();
        if (this.nextSpi(null) == null) {
            throw new NoSuchAlgorithmException(algorithm + " KeyFactory not available");
        }
    }
    
    public static KeyFactory getInstance(final String s) throws NoSuchAlgorithmException {
        return new KeyFactory(s);
    }
    
    public static KeyFactory getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, s, s2);
        return new KeyFactory((KeyFactorySpi)instance.impl, instance.provider, s);
    }
    
    public static KeyFactory getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, s, provider);
        return new KeyFactory((KeyFactorySpi)instance.impl, instance.provider, s);
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
    
    private KeyFactorySpi nextSpi(final KeyFactorySpi keyFactorySpi) {
        synchronized (this.lock) {
            if (keyFactorySpi != null && keyFactorySpi != this.spi) {
                return this.spi;
            }
            if (this.serviceIterator == null) {
                return null;
            }
            while (this.serviceIterator.hasNext()) {
                final Provider.Service service = this.serviceIterator.next();
                try {
                    final Object instance = service.newInstance(null);
                    if (!(instance instanceof KeyFactorySpi)) {
                        continue;
                    }
                    final KeyFactorySpi spi = (KeyFactorySpi)instance;
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
    
    public final PublicKey generatePublic(final KeySpec keySpec) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGeneratePublic(keySpec);
        }
        Throwable t = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        try {
            return keyFactorySpi.engineGeneratePublic(keySpec);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            keyFactorySpi = this.nextSpi(keyFactorySpi);
            if (keyFactorySpi != null) {
                return keyFactorySpi.engineGeneratePublic(keySpec);
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            if (t instanceof InvalidKeySpecException) {
                throw (InvalidKeySpecException)t;
            }
            throw new InvalidKeySpecException("Could not generate public key", t);
        }
    }
    
    public final PrivateKey generatePrivate(final KeySpec keySpec) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGeneratePrivate(keySpec);
        }
        Throwable t = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        try {
            return keyFactorySpi.engineGeneratePrivate(keySpec);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            keyFactorySpi = this.nextSpi(keyFactorySpi);
            if (keyFactorySpi != null) {
                return keyFactorySpi.engineGeneratePrivate(keySpec);
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            if (t instanceof InvalidKeySpecException) {
                throw (InvalidKeySpecException)t;
            }
            throw new InvalidKeySpecException("Could not generate private key", t);
        }
    }
    
    public final <T extends KeySpec> T getKeySpec(final Key key, final Class<T> clazz) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGetKeySpec(key, clazz);
        }
        Throwable t = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        try {
            return keyFactorySpi.engineGetKeySpec(key, clazz);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            keyFactorySpi = this.nextSpi(keyFactorySpi);
            if (keyFactorySpi != null) {
                return keyFactorySpi.engineGetKeySpec(key, clazz);
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            if (t instanceof InvalidKeySpecException) {
                throw (InvalidKeySpecException)t;
            }
            throw new InvalidKeySpecException("Could not get key spec", t);
        }
    }
    
    public final Key translateKey(final Key key) throws InvalidKeyException {
        if (this.serviceIterator == null) {
            return this.spi.engineTranslateKey(key);
        }
        Throwable t = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        try {
            return keyFactorySpi.engineTranslateKey(key);
        }
        catch (Exception ex) {
            if (t == null) {
                t = ex;
            }
            keyFactorySpi = this.nextSpi(keyFactorySpi);
            if (keyFactorySpi != null) {
                return keyFactorySpi.engineTranslateKey(key);
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            if (t instanceof InvalidKeyException) {
                throw (InvalidKeyException)t;
            }
            throw new InvalidKeyException("Could not translate key", t);
        }
    }
    
    static {
        debug = Debug.getInstance("jca", "KeyFactory");
    }
}
