package javax.crypto;

import sun.security.util.*;
import java.util.*;
import sun.security.jca.*;
import java.security.spec.*;
import java.security.*;
import java.nio.*;

public class Mac implements Cloneable
{
    private static final Debug debug;
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private Provider provider;
    private MacSpi spi;
    private final String algorithm;
    private boolean initialized;
    private Provider.Service firstService;
    private Iterator<Provider.Service> serviceIterator;
    private final Object lock;
    private static int warnCount;
    
    protected Mac(final MacSpi spi, final Provider provider, final String algorithm) {
        this.initialized = false;
        this.spi = spi;
        this.provider = provider;
        this.algorithm = algorithm;
        this.serviceIterator = null;
        this.lock = null;
    }
    
    private Mac(final Provider.Service firstService, final Iterator<Provider.Service> serviceIterator, final String algorithm) {
        this.initialized = false;
        this.firstService = firstService;
        this.serviceIterator = serviceIterator;
        this.algorithm = algorithm;
        this.lock = new Object();
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static final Mac getInstance(final String s) throws NoSuchAlgorithmException {
        final Iterator<Provider.Service> iterator = GetInstance.getServices("Mac", s).iterator();
        while (iterator.hasNext()) {
            final Provider.Service service = iterator.next();
            if (!JceSecurity.canUseProvider(service.getProvider())) {
                continue;
            }
            return new Mac(service, iterator, s);
        }
        throw new NoSuchAlgorithmException("Algorithm " + s + " not available");
    }
    
    public static final Mac getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = JceSecurity.getInstance("Mac", MacSpi.class, s, s2);
        return new Mac((MacSpi)instance.impl, instance.provider, s);
    }
    
    public static final Mac getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = JceSecurity.getInstance("Mac", MacSpi.class, s, provider);
        return new Mac((MacSpi)instance.impl, instance.provider, s);
    }
    
    void chooseFirstProvider() {
        if (this.spi != null || this.serviceIterator == null) {
            return;
        }
        synchronized (this.lock) {
            if (this.spi != null) {
                return;
            }
            if (Mac.debug != null) {
                final int n = --Mac.warnCount;
                if (n >= 0) {
                    Mac.debug.println("Mac.init() not first method called, disabling delayed provider selection");
                    if (n == 0) {
                        Mac.debug.println("Further warnings of this type will be suppressed");
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
                    if (!(instance instanceof MacSpi)) {
                        continue;
                    }
                    this.spi = (MacSpi)instance;
                    this.provider = firstService.getProvider();
                    this.firstService = null;
                    this.serviceIterator = null;
                    return;
                }
                catch (NoSuchAlgorithmException ex) {
                    t = ex;
                    continue;
                }
                break;
            }
            final ProviderException ex2 = new ProviderException("Could not construct MacSpi instance");
            if (t != null) {
                ex2.initCause(t);
            }
            throw ex2;
        }
    }
    
    private void chooseProvider(final Key key, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        synchronized (this.lock) {
            if (this.spi != null) {
                this.spi.engineInit(key, algorithmParameterSpec);
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
                    final MacSpi spi = (MacSpi)firstService.newInstance(null);
                    spi.engineInit(key, algorithmParameterSpec);
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
    
    public final int getMacLength() {
        this.chooseFirstProvider();
        return this.spi.engineGetMacLength();
    }
    
    public final void init(final Key key) throws InvalidKeyException {
        try {
            if (this.spi != null) {
                this.spi.engineInit(key, null);
            }
            else {
                this.chooseProvider(key, null);
            }
        }
        catch (InvalidAlgorithmParameterException ex) {
            throw new InvalidKeyException("init() failed", ex);
        }
        this.initialized = true;
        if (!Mac.skipDebug && Mac.pdebug != null) {
            Mac.pdebug.println("Mac." + this.algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final void init(final Key key, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.spi != null) {
            this.spi.engineInit(key, algorithmParameterSpec);
        }
        else {
            this.chooseProvider(key, algorithmParameterSpec);
        }
        this.initialized = true;
        if (!Mac.skipDebug && Mac.pdebug != null) {
            Mac.pdebug.println("Mac." + this.algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final void update(final byte b) throws IllegalStateException {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        this.spi.engineUpdate(b);
    }
    
    public final void update(final byte[] array) throws IllegalStateException {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        if (array != null) {
            this.spi.engineUpdate(array, 0, array.length);
        }
    }
    
    public final void update(final byte[] array, final int n, final int n2) throws IllegalStateException {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        if (array != null) {
            if (n < 0 || n2 > array.length - n || n2 < 0) {
                throw new IllegalArgumentException("Bad arguments");
            }
            this.spi.engineUpdate(array, n, n2);
        }
    }
    
    public final void update(final ByteBuffer byteBuffer) {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        if (byteBuffer == null) {
            throw new IllegalArgumentException("Buffer must not be null");
        }
        this.spi.engineUpdate(byteBuffer);
    }
    
    public final byte[] doFinal() throws IllegalStateException {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        final byte[] engineDoFinal = this.spi.engineDoFinal();
        this.spi.engineReset();
        return engineDoFinal;
    }
    
    public final void doFinal(final byte[] array, final int n) throws ShortBufferException, IllegalStateException {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        final int macLength = this.getMacLength();
        if (array == null || array.length - n < macLength) {
            throw new ShortBufferException("Cannot store MAC in output buffer");
        }
        System.arraycopy(this.doFinal(), 0, array, n, macLength);
    }
    
    public final byte[] doFinal(final byte[] array) throws IllegalStateException {
        this.chooseFirstProvider();
        if (!this.initialized) {
            throw new IllegalStateException("MAC not initialized");
        }
        this.update(array);
        return this.doFinal();
    }
    
    public final void reset() {
        this.chooseFirstProvider();
        this.spi.engineReset();
    }
    
    public final Object clone() throws CloneNotSupportedException {
        this.chooseFirstProvider();
        final Mac mac = (Mac)super.clone();
        mac.spi = (MacSpi)this.spi.clone();
        return mac;
    }
    
    static {
        debug = Debug.getInstance("jca", "Mac");
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("mac"));
        Mac.warnCount = 10;
    }
}
