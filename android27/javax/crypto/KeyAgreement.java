package javax.crypto;

import sun.security.util.*;
import java.util.*;
import sun.security.jca.*;
import java.security.spec.*;
import java.security.*;

public class KeyAgreement
{
    private static final Debug debug;
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private Provider provider;
    private KeyAgreementSpi spi;
    private final String algorithm;
    private Provider.Service firstService;
    private Iterator<Provider.Service> serviceIterator;
    private final Object lock;
    private static int warnCount;
    private static final int I_NO_PARAMS = 1;
    private static final int I_PARAMS = 2;
    
    protected KeyAgreement(final KeyAgreementSpi spi, final Provider provider, final String algorithm) {
        this.spi = spi;
        this.provider = provider;
        this.algorithm = algorithm;
        this.lock = null;
    }
    
    private KeyAgreement(final Provider.Service firstService, final Iterator<Provider.Service> serviceIterator, final String algorithm) {
        this.firstService = firstService;
        this.serviceIterator = serviceIterator;
        this.algorithm = algorithm;
        this.lock = new Object();
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static final KeyAgreement getInstance(final String s) throws NoSuchAlgorithmException {
        final Iterator<Provider.Service> iterator = GetInstance.getServices("KeyAgreement", s).iterator();
        while (iterator.hasNext()) {
            final Provider.Service service = iterator.next();
            if (!JceSecurity.canUseProvider(service.getProvider())) {
                continue;
            }
            return new KeyAgreement(service, iterator, s);
        }
        throw new NoSuchAlgorithmException("Algorithm " + s + " not available");
    }
    
    public static final KeyAgreement getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = JceSecurity.getInstance("KeyAgreement", KeyAgreementSpi.class, s, s2);
        return new KeyAgreement((KeyAgreementSpi)instance.impl, instance.provider, s);
    }
    
    public static final KeyAgreement getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = JceSecurity.getInstance("KeyAgreement", KeyAgreementSpi.class, s, provider);
        return new KeyAgreement((KeyAgreementSpi)instance.impl, instance.provider, s);
    }
    
    void chooseFirstProvider() {
        if (this.spi != null) {
            return;
        }
        synchronized (this.lock) {
            if (this.spi != null) {
                return;
            }
            if (KeyAgreement.debug != null) {
                final int n = --KeyAgreement.warnCount;
                if (n >= 0) {
                    KeyAgreement.debug.println("KeyAgreement.init() not first method called, disabling delayed provider selection");
                    if (n == 0) {
                        KeyAgreement.debug.println("Further warnings of this type will be suppressed");
                    }
                    new Exception("Call trace").printStackTrace();
                }
            }
            Throwable t = null;
            while (this.firstService != null || this.serviceIterator.hasNext()) {
                Provider.Service firstService;
                if (this.firstService != null) {
                    firstService = this.firstService;
                    this.firstService = null;
                }
                else {
                    firstService = this.serviceIterator.next();
                }
                if (!JceSecurity.canUseProvider(firstService.getProvider())) {
                    continue;
                }
                try {
                    final Object instance = firstService.newInstance(null);
                    if (!(instance instanceof KeyAgreementSpi)) {
                        continue;
                    }
                    this.spi = (KeyAgreementSpi)instance;
                    this.provider = firstService.getProvider();
                    this.firstService = null;
                    this.serviceIterator = null;
                    return;
                }
                catch (Exception ex) {
                    t = ex;
                    continue;
                }
                break;
            }
            final ProviderException ex2 = new ProviderException("Could not construct KeyAgreementSpi instance");
            if (t != null) {
                ex2.initCause(t);
            }
            throw ex2;
        }
    }
    
    private void implInit(final KeyAgreementSpi keyAgreementSpi, final int n, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (n == 1) {
            keyAgreementSpi.engineInit(key, secureRandom);
        }
        else {
            keyAgreementSpi.engineInit(key, algorithmParameterSpec, secureRandom);
        }
    }
    
    private void chooseProvider(final int n, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        synchronized (this.lock) {
            if (this.spi != null) {
                this.implInit(this.spi, n, key, algorithmParameterSpec, secureRandom);
                return;
            }
            Throwable t = null;
            while (this.firstService != null || this.serviceIterator.hasNext()) {
                Provider.Service firstService;
                if (this.firstService != null) {
                    firstService = this.firstService;
                    this.firstService = null;
                }
                else {
                    firstService = this.serviceIterator.next();
                }
                if (!firstService.supportsParameter(key)) {
                    continue;
                }
                if (!JceSecurity.canUseProvider(firstService.getProvider())) {
                    continue;
                }
                try {
                    final KeyAgreementSpi spi = (KeyAgreementSpi)firstService.newInstance(null);
                    this.implInit(spi, n, key, algorithmParameterSpec, secureRandom);
                    this.provider = firstService.getProvider();
                    this.spi = spi;
                    this.firstService = null;
                    this.serviceIterator = null;
                    return;
                }
                catch (Exception ex) {
                    if (t != null) {
                        continue;
                    }
                    t = ex;
                    continue;
                }
                break;
            }
            if (t instanceof InvalidKeyException) {
                throw (InvalidKeyException)t;
            }
            if (t instanceof InvalidAlgorithmParameterException) {
                throw (InvalidAlgorithmParameterException)t;
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            throw new InvalidKeyException("No installed provider supports this key: " + ((key != null) ? key.getClass().getName() : "(null)"), t);
        }
    }
    
    public final Provider getProvider() {
        this.chooseFirstProvider();
        return this.provider;
    }
    
    public final void init(final Key key) throws InvalidKeyException {
        this.init(key, JceSecurity.RANDOM);
    }
    
    public final void init(final Key key, final SecureRandom secureRandom) throws InvalidKeyException {
        if (this.spi != null) {
            this.spi.engineInit(key, secureRandom);
        }
        else {
            try {
                this.chooseProvider(1, key, null, secureRandom);
            }
            catch (InvalidAlgorithmParameterException ex) {
                throw new InvalidKeyException(ex);
            }
        }
        if (!KeyAgreement.skipDebug && KeyAgreement.pdebug != null) {
            KeyAgreement.pdebug.println("KeyAgreement." + this.algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final void init(final Key key, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.init(key, algorithmParameterSpec, JceSecurity.RANDOM);
    }
    
    public final void init(final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.spi != null) {
            this.spi.engineInit(key, algorithmParameterSpec, secureRandom);
        }
        else {
            this.chooseProvider(2, key, algorithmParameterSpec, secureRandom);
        }
        if (!KeyAgreement.skipDebug && KeyAgreement.pdebug != null) {
            KeyAgreement.pdebug.println("KeyAgreement." + this.algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final Key doPhase(final Key key, final boolean b) throws InvalidKeyException, IllegalStateException {
        this.chooseFirstProvider();
        return this.spi.engineDoPhase(key, b);
    }
    
    public final byte[] generateSecret() throws IllegalStateException {
        this.chooseFirstProvider();
        return this.spi.engineGenerateSecret();
    }
    
    public final int generateSecret(final byte[] array, final int n) throws IllegalStateException, ShortBufferException {
        this.chooseFirstProvider();
        return this.spi.engineGenerateSecret(array, n);
    }
    
    public final SecretKey generateSecret(final String s) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        this.chooseFirstProvider();
        return this.spi.engineGenerateSecret(s);
    }
    
    static {
        debug = Debug.getInstance("jca", "KeyAgreement");
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("keyagreement"));
        KeyAgreement.warnCount = 10;
    }
}
