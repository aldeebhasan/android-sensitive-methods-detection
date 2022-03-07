package javax.crypto;

import sun.security.util.*;
import java.util.*;
import java.security.spec.*;
import sun.security.jca.*;
import java.security.*;

public class KeyGenerator
{
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private static final int I_NONE = 1;
    private static final int I_RANDOM = 2;
    private static final int I_PARAMS = 3;
    private static final int I_SIZE = 4;
    private Provider provider;
    private volatile KeyGeneratorSpi spi;
    private final String algorithm;
    private final Object lock;
    private Iterator<Provider.Service> serviceIterator;
    private int initType;
    private int initKeySize;
    private AlgorithmParameterSpec initParams;
    private SecureRandom initRandom;
    
    protected KeyGenerator(final KeyGeneratorSpi spi, final Provider provider, final String algorithm) {
        this.lock = new Object();
        this.spi = spi;
        this.provider = provider;
        this.algorithm = algorithm;
        if (!KeyGenerator.skipDebug && KeyGenerator.pdebug != null) {
            KeyGenerator.pdebug.println("KeyGenerator." + algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    private KeyGenerator(final String algorithm) throws NoSuchAlgorithmException {
        this.lock = new Object();
        this.algorithm = algorithm;
        this.serviceIterator = GetInstance.getServices("KeyGenerator", algorithm).iterator();
        this.initType = 1;
        if (this.nextSpi(null, false) == null) {
            throw new NoSuchAlgorithmException(algorithm + " KeyGenerator not available");
        }
        if (!KeyGenerator.skipDebug && KeyGenerator.pdebug != null) {
            KeyGenerator.pdebug.println("KeyGenerator." + algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static final KeyGenerator getInstance(final String s) throws NoSuchAlgorithmException {
        return new KeyGenerator(s);
    }
    
    public static final KeyGenerator getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = JceSecurity.getInstance("KeyGenerator", KeyGeneratorSpi.class, s, s2);
        return new KeyGenerator((KeyGeneratorSpi)instance.impl, instance.provider, s);
    }
    
    public static final KeyGenerator getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = JceSecurity.getInstance("KeyGenerator", KeyGeneratorSpi.class, s, provider);
        return new KeyGenerator((KeyGeneratorSpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        synchronized (this.lock) {
            this.disableFailover();
            return this.provider;
        }
    }
    
    private KeyGeneratorSpi nextSpi(final KeyGeneratorSpi keyGeneratorSpi, final boolean b) {
        synchronized (this.lock) {
            if (keyGeneratorSpi != null && keyGeneratorSpi != this.spi) {
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
                    if (!(instance instanceof KeyGeneratorSpi)) {
                        continue;
                    }
                    final KeyGeneratorSpi spi = (KeyGeneratorSpi)instance;
                    if (b) {
                        if (this.initType == 4) {
                            spi.engineInit(this.initKeySize, this.initRandom);
                        }
                        else if (this.initType == 3) {
                            spi.engineInit(this.initParams, this.initRandom);
                        }
                        else if (this.initType == 2) {
                            spi.engineInit(this.initRandom);
                        }
                        else if (this.initType != 1) {
                            throw new AssertionError((Object)("KeyGenerator initType: " + this.initType));
                        }
                    }
                    this.provider = service.getProvider();
                    return this.spi = spi;
                }
                catch (Exception ex) {
                    continue;
                }
                break;
            }
            this.disableFailover();
            return null;
        }
    }
    
    void disableFailover() {
        this.serviceIterator = null;
        this.initType = 0;
        this.initParams = null;
        this.initRandom = null;
    }
    
    public final void init(final SecureRandom initRandom) {
        if (this.serviceIterator == null) {
            this.spi.engineInit(initRandom);
            return;
        }
        RuntimeException ex = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        while (true) {
            try {
                keyGeneratorSpi.engineInit(initRandom);
                this.initType = 2;
                this.initKeySize = 0;
                this.initParams = null;
                this.initRandom = initRandom;
            }
            catch (RuntimeException ex2) {
                if (ex == null) {
                    ex = ex2;
                }
                keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, false);
                if (keyGeneratorSpi == null) {
                    throw ex;
                }
                continue;
            }
            break;
        }
    }
    
    public final void init(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.init(algorithmParameterSpec, JceSecurity.RANDOM);
    }
    
    public final void init(final AlgorithmParameterSpec initParams, final SecureRandom initRandom) throws InvalidAlgorithmParameterException {
        if (this.serviceIterator == null) {
            this.spi.engineInit(initParams, initRandom);
            return;
        }
        Throwable t = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        while (true) {
            try {
                keyGeneratorSpi.engineInit(initParams, initRandom);
                this.initType = 3;
                this.initKeySize = 0;
                this.initParams = initParams;
                this.initRandom = initRandom;
            }
            catch (Exception ex) {
                if (t == null) {
                    t = ex;
                }
                keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, false);
                if (keyGeneratorSpi != null) {
                    continue;
                }
                if (t instanceof InvalidAlgorithmParameterException) {
                    throw (InvalidAlgorithmParameterException)t;
                }
                if (t instanceof RuntimeException) {
                    throw (RuntimeException)t;
                }
                throw new InvalidAlgorithmParameterException("init() failed", t);
            }
            break;
        }
    }
    
    public final void init(final int n) {
        this.init(n, JceSecurity.RANDOM);
    }
    
    public final void init(final int initKeySize, final SecureRandom initRandom) {
        if (this.serviceIterator == null) {
            this.spi.engineInit(initKeySize, initRandom);
            return;
        }
        RuntimeException ex = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        while (true) {
            try {
                keyGeneratorSpi.engineInit(initKeySize, initRandom);
                this.initType = 4;
                this.initKeySize = initKeySize;
                this.initParams = null;
                this.initRandom = initRandom;
            }
            catch (RuntimeException ex2) {
                if (ex == null) {
                    ex = ex2;
                }
                keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, false);
                if (keyGeneratorSpi == null) {
                    throw ex;
                }
                continue;
            }
            break;
        }
    }
    
    public final SecretKey generateKey() {
        if (this.serviceIterator == null) {
            return this.spi.engineGenerateKey();
        }
        RuntimeException ex = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        try {
            return keyGeneratorSpi.engineGenerateKey();
        }
        catch (RuntimeException ex2) {
            if (ex == null) {
                ex = ex2;
            }
            keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, true);
            if (keyGeneratorSpi == null) {
                throw ex;
            }
            return keyGeneratorSpi.engineGenerateKey();
        }
    }
    
    static {
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("keygenerator"));
    }
}
