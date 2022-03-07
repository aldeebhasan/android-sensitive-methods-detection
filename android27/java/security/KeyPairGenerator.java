package java.security;

import sun.security.util.*;
import java.util.*;
import sun.security.jca.*;
import java.security.spec.*;

public abstract class KeyPairGenerator extends KeyPairGeneratorSpi
{
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private final String algorithm;
    Provider provider;
    
    protected KeyPairGenerator(final String algorithm) {
        this.algorithm = algorithm;
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    private static KeyPairGenerator getInstance(final GetInstance.Instance instance, final String s) {
        KeyPairGenerator keyPairGenerator;
        if (instance.impl instanceof KeyPairGenerator) {
            keyPairGenerator = (KeyPairGenerator)instance.impl;
        }
        else {
            keyPairGenerator = new Delegate((KeyPairGeneratorSpi)instance.impl, s);
        }
        keyPairGenerator.provider = instance.provider;
        if (!KeyPairGenerator.skipDebug && KeyPairGenerator.pdebug != null) {
            KeyPairGenerator.pdebug.println("KeyPairGenerator." + s + " algorithm from: " + keyPairGenerator.provider.getName());
        }
        return keyPairGenerator;
    }
    
    public static KeyPairGenerator getInstance(final String s) throws NoSuchAlgorithmException {
        final Iterator<Provider.Service> iterator = GetInstance.getServices("KeyPairGenerator", s).iterator();
        if (!iterator.hasNext()) {
            throw new NoSuchAlgorithmException(s + " KeyPairGenerator not available");
        }
        NoSuchAlgorithmException ex = null;
        while (true) {
            final Provider.Service service = iterator.next();
            try {
                final GetInstance.Instance instance = GetInstance.getInstance(service, KeyPairGeneratorSpi.class);
                if (instance.impl instanceof KeyPairGenerator) {
                    return getInstance(instance, s);
                }
                return new Delegate(instance, iterator, s);
            }
            catch (NoSuchAlgorithmException ex2) {
                if (ex == null) {
                    ex = ex2;
                }
                if (!iterator.hasNext()) {
                    throw ex;
                }
                continue;
            }
        }
    }
    
    public static KeyPairGenerator getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        return getInstance(GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, s, s2), s);
    }
    
    public static KeyPairGenerator getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        return getInstance(GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, s, provider), s);
    }
    
    public final Provider getProvider() {
        this.disableFailover();
        return this.provider;
    }
    
    void disableFailover() {
    }
    
    public void initialize(final int n) {
        this.initialize(n, JCAUtil.getSecureRandom());
    }
    
    @Override
    public void initialize(final int n, final SecureRandom secureRandom) {
    }
    
    public void initialize(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.initialize(algorithmParameterSpec, JCAUtil.getSecureRandom());
    }
    
    @Override
    public void initialize(final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
    }
    
    public final KeyPair genKeyPair() {
        return this.generateKeyPair();
    }
    
    @Override
    public KeyPair generateKeyPair() {
        return null;
    }
    
    static {
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("keypairgenerator"));
    }
    
    private static final class Delegate extends KeyPairGenerator
    {
        private volatile KeyPairGeneratorSpi spi;
        private final Object lock;
        private Iterator<Provider.Service> serviceIterator;
        private static final int I_NONE = 1;
        private static final int I_SIZE = 2;
        private static final int I_PARAMS = 3;
        private int initType;
        private int initKeySize;
        private AlgorithmParameterSpec initParams;
        private SecureRandom initRandom;
        
        Delegate(final KeyPairGeneratorSpi spi, final String s) {
            super(s);
            this.lock = new Object();
            this.spi = spi;
        }
        
        Delegate(final GetInstance.Instance instance, final Iterator<Provider.Service> serviceIterator, final String s) {
            super(s);
            this.lock = new Object();
            this.spi = (KeyPairGeneratorSpi)instance.impl;
            this.provider = instance.provider;
            this.serviceIterator = serviceIterator;
            this.initType = 1;
            if (!KeyPairGenerator.skipDebug && KeyPairGenerator.pdebug != null) {
                KeyPairGenerator.pdebug.println("KeyPairGenerator." + s + " algorithm from: " + this.provider.getName());
            }
        }
        
        private KeyPairGeneratorSpi nextSpi(final KeyPairGeneratorSpi keyPairGeneratorSpi, final boolean b) {
            synchronized (this.lock) {
                if (keyPairGeneratorSpi != null && keyPairGeneratorSpi != this.spi) {
                    return this.spi;
                }
                if (this.serviceIterator == null) {
                    return null;
                }
                while (this.serviceIterator.hasNext()) {
                    final Provider.Service service = this.serviceIterator.next();
                    try {
                        final Object instance = service.newInstance(null);
                        if (!(instance instanceof KeyPairGeneratorSpi)) {
                            continue;
                        }
                        if (instance instanceof KeyPairGenerator) {
                            continue;
                        }
                        final KeyPairGeneratorSpi spi = (KeyPairGeneratorSpi)instance;
                        if (b) {
                            if (this.initType == 2) {
                                spi.initialize(this.initKeySize, this.initRandom);
                            }
                            else if (this.initType == 3) {
                                spi.initialize(this.initParams, this.initRandom);
                            }
                            else if (this.initType != 1) {
                                throw new AssertionError((Object)("KeyPairGenerator initType: " + this.initType));
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
        
        @Override
        void disableFailover() {
            this.serviceIterator = null;
            this.initType = 0;
            this.initParams = null;
            this.initRandom = null;
        }
        
        @Override
        public void initialize(final int initKeySize, final SecureRandom initRandom) {
            if (this.serviceIterator == null) {
                this.spi.initialize(initKeySize, initRandom);
                return;
            }
            RuntimeException ex = null;
            KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
            while (true) {
                try {
                    keyPairGeneratorSpi.initialize(initKeySize, initRandom);
                    this.initType = 2;
                    this.initKeySize = initKeySize;
                    this.initParams = null;
                    this.initRandom = initRandom;
                }
                catch (RuntimeException ex2) {
                    if (ex == null) {
                        ex = ex2;
                    }
                    keyPairGeneratorSpi = this.nextSpi(keyPairGeneratorSpi, false);
                    if (keyPairGeneratorSpi == null) {
                        throw ex;
                    }
                    continue;
                }
                break;
            }
        }
        
        @Override
        public void initialize(final AlgorithmParameterSpec initParams, final SecureRandom initRandom) throws InvalidAlgorithmParameterException {
            if (this.serviceIterator == null) {
                this.spi.initialize(initParams, initRandom);
                return;
            }
            Exception ex = null;
            KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
            while (true) {
                try {
                    keyPairGeneratorSpi.initialize(initParams, initRandom);
                    this.initType = 3;
                    this.initKeySize = 0;
                    this.initParams = initParams;
                    this.initRandom = initRandom;
                }
                catch (Exception ex2) {
                    if (ex == null) {
                        ex = ex2;
                    }
                    keyPairGeneratorSpi = this.nextSpi(keyPairGeneratorSpi, false);
                    if (keyPairGeneratorSpi != null) {
                        continue;
                    }
                    if (ex instanceof RuntimeException) {
                        throw (RuntimeException)ex;
                    }
                    throw (InvalidAlgorithmParameterException)ex;
                }
                break;
            }
        }
        
        @Override
        public KeyPair generateKeyPair() {
            if (this.serviceIterator == null) {
                return this.spi.generateKeyPair();
            }
            RuntimeException ex = null;
            KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
            try {
                return keyPairGeneratorSpi.generateKeyPair();
            }
            catch (RuntimeException ex2) {
                if (ex == null) {
                    ex = ex2;
                }
                keyPairGeneratorSpi = this.nextSpi(keyPairGeneratorSpi, true);
                if (keyPairGeneratorSpi == null) {
                    throw ex;
                }
                return keyPairGeneratorSpi.generateKeyPair();
            }
        }
    }
}
