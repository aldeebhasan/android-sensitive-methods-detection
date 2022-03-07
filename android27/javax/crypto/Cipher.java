package javax.crypto;

import sun.security.util.*;
import sun.security.jca.*;
import java.security.spec.*;
import java.security.cert.*;
import java.util.*;
import java.security.*;
import java.nio.*;
import javax.crypto.spec.*;
import java.util.regex.*;
import java.util.concurrent.*;

public class Cipher
{
    private static final Debug debug;
    private static final Debug pdebug;
    private static final boolean skipDebug;
    public static final int ENCRYPT_MODE = 1;
    public static final int DECRYPT_MODE = 2;
    public static final int WRAP_MODE = 3;
    public static final int UNWRAP_MODE = 4;
    public static final int PUBLIC_KEY = 1;
    public static final int PRIVATE_KEY = 2;
    public static final int SECRET_KEY = 3;
    private Provider provider;
    private CipherSpi spi;
    private String transformation;
    private CryptoPermission cryptoPerm;
    private ExemptionMechanism exmech;
    private boolean initialized;
    private int opmode;
    private static final String KEY_USAGE_EXTENSION_OID = "2.5.29.15";
    private CipherSpi firstSpi;
    private Provider.Service firstService;
    private Iterator<Provider.Service> serviceIterator;
    private List<Transform> transforms;
    private final Object lock;
    private static final String ATTR_MODE = "SupportedModes";
    private static final String ATTR_PAD = "SupportedPaddings";
    private static final int S_NO = 0;
    private static final int S_MAYBE = 1;
    private static final int S_YES = 2;
    private static int warnCount;
    private static final int I_KEY = 1;
    private static final int I_PARAMSPEC = 2;
    private static final int I_PARAMS = 3;
    private static final int I_CERT = 4;
    
    protected Cipher(final CipherSpi spi, final Provider provider, final String transformation) {
        this.initialized = false;
        this.opmode = 0;
        if (!JceSecurityManager.INSTANCE.isCallerTrusted()) {
            throw new NullPointerException();
        }
        this.spi = spi;
        this.provider = provider;
        this.transformation = transformation;
        this.cryptoPerm = CryptoAllPermission.INSTANCE;
        this.lock = null;
    }
    
    Cipher(final CipherSpi spi, final String transformation) {
        this.initialized = false;
        this.opmode = 0;
        this.spi = spi;
        this.transformation = transformation;
        this.cryptoPerm = CryptoAllPermission.INSTANCE;
        this.lock = null;
    }
    
    private Cipher(final CipherSpi firstSpi, final Provider.Service firstService, final Iterator<Provider.Service> serviceIterator, final String transformation, final List<Transform> transforms) {
        this.initialized = false;
        this.opmode = 0;
        this.firstSpi = firstSpi;
        this.firstService = firstService;
        this.serviceIterator = serviceIterator;
        this.transforms = transforms;
        this.transformation = transformation;
        this.lock = new Object();
    }
    
    private static String[] tokenizeTransformation(final String s) throws NoSuchAlgorithmException {
        if (s == null) {
            throw new NoSuchAlgorithmException("No transformation given");
        }
        final String[] array = new String[3];
        int n = 0;
        final StringTokenizer stringTokenizer = new StringTokenizer(s, "/");
        try {
            while (stringTokenizer.hasMoreTokens() && n < 3) {
                array[n++] = stringTokenizer.nextToken().trim();
            }
            if (n == 0 || n == 2) {
                throw new NoSuchAlgorithmException("Invalid transformation format:" + s);
            }
            if (n == 3 && stringTokenizer.hasMoreTokens()) {
                array[2] += stringTokenizer.nextToken("\r\n");
            }
        }
        catch (NoSuchElementException ex) {
            throw new NoSuchAlgorithmException("Invalid transformation format:" + s);
        }
        if (array[0] == null || array[0].length() == 0) {
            throw new NoSuchAlgorithmException("Invalid transformation:algorithm not specified-" + s);
        }
        return array;
    }
    
    private static List<Transform> getTransforms(final String s) throws NoSuchAlgorithmException {
        final String[] tokenizeTransformation = tokenizeTransformation(s);
        final String s2 = tokenizeTransformation[0];
        String s3 = tokenizeTransformation[1];
        String s4 = tokenizeTransformation[2];
        if (s3 != null && s3.length() == 0) {
            s3 = null;
        }
        if (s4 != null && s4.length() == 0) {
            s4 = null;
        }
        if (s3 == null && s4 == null) {
            return Collections.singletonList(new Transform(s2, "", null, null));
        }
        final ArrayList<Transform> list = new ArrayList<Transform>(4);
        list.add(new Transform(s2, "/" + s3 + "/" + s4, null, null));
        list.add(new Transform(s2, "/" + s3, null, s4));
        list.add(new Transform(s2, "//" + s4, s3, null));
        list.add(new Transform(s2, "", s3, s4));
        return list;
    }
    
    private static Transform getTransform(final Provider.Service service, final List<Transform> list) {
        final String upperCase = service.getAlgorithm().toUpperCase(Locale.ENGLISH);
        for (final Transform transform : list) {
            if (upperCase.endsWith(transform.suffix)) {
                return transform;
            }
        }
        return null;
    }
    
    public static final Cipher getInstance(final String s) throws NoSuchAlgorithmException, NoSuchPaddingException {
        final List<Transform> transforms = getTransforms(s);
        final ArrayList list = new ArrayList<ServiceId>(transforms.size());
        final Iterator<Transform> iterator = transforms.iterator();
        while (iterator.hasNext()) {
            list.add(new ServiceId("Cipher", iterator.next().transform));
        }
        final Iterator<Provider.Service> iterator2 = GetInstance.getServices((List<ServiceId>)list).iterator();
        Throwable t = null;
        while (iterator2.hasNext()) {
            final Provider.Service service = iterator2.next();
            if (!JceSecurity.canUseProvider(service.getProvider())) {
                continue;
            }
            final Transform transform = getTransform(service, transforms);
            if (transform == null) {
                continue;
            }
            final int supportsModePadding = transform.supportsModePadding(service);
            if (supportsModePadding == 0) {
                continue;
            }
            if (supportsModePadding == 2) {
                return new Cipher(null, service, iterator2, s, transforms);
            }
            try {
                final CipherSpi modePadding = (CipherSpi)service.newInstance(null);
                transform.setModePadding(modePadding);
                return new Cipher(modePadding, service, iterator2, s, transforms);
            }
            catch (Exception ex) {
                t = ex;
                continue;
            }
            break;
        }
        throw new NoSuchAlgorithmException("Cannot find any provider supporting " + s, t);
    }
    
    public static final Cipher getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("Missing provider");
        }
        final Provider provider = Security.getProvider(s2);
        if (provider == null) {
            throw new NoSuchProviderException("No such provider: " + s2);
        }
        return getInstance(s, provider);
    }
    
    public static final Cipher getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (provider == null) {
            throw new IllegalArgumentException("Missing provider");
        }
        Throwable t = null;
        final List<Transform> transforms = getTransforms(s);
        int n = 0;
        String pad = null;
        for (final Transform transform : transforms) {
            final Provider.Service service = provider.getService("Cipher", transform.transform);
            if (service == null) {
                continue;
            }
            if (n == 0) {
                final Exception verificationResult = JceSecurity.getVerificationResult(provider);
                if (verificationResult != null) {
                    throw new SecurityException("JCE cannot authenticate the provider " + provider.getName(), verificationResult);
                }
                n = 1;
            }
            if (transform.supportsMode(service) == 0) {
                continue;
            }
            if (transform.supportsPadding(service) != 0) {
                try {
                    final CipherSpi modePadding = (CipherSpi)service.newInstance(null);
                    transform.setModePadding(modePadding);
                    final Cipher cipher = new Cipher(modePadding, s);
                    cipher.provider = service.getProvider();
                    cipher.initCryptoPermission();
                    return cipher;
                }
                catch (Exception ex) {
                    t = ex;
                    continue;
                }
                break;
            }
            pad = transform.pad;
        }
        if (t instanceof NoSuchPaddingException) {
            throw (NoSuchPaddingException)t;
        }
        if (pad != null) {
            throw new NoSuchPaddingException("Padding not supported: " + pad);
        }
        throw new NoSuchAlgorithmException("No such algorithm: " + s, t);
    }
    
    private void initCryptoPermission() throws NoSuchAlgorithmException {
        if (!JceSecurity.isRestricted()) {
            this.cryptoPerm = CryptoAllPermission.INSTANCE;
            this.exmech = null;
            return;
        }
        this.cryptoPerm = getConfiguredPermission(this.transformation);
        final String exemptionMechanism = this.cryptoPerm.getExemptionMechanism();
        if (exemptionMechanism != null) {
            this.exmech = ExemptionMechanism.getInstance(exemptionMechanism);
        }
    }
    
    void chooseFirstProvider() {
        if (this.spi != null) {
            return;
        }
        synchronized (this.lock) {
            if (this.spi != null) {
                return;
            }
            if (Cipher.debug != null) {
                final int n = --Cipher.warnCount;
                if (n >= 0) {
                    Cipher.debug.println("Cipher.init() not first method called, disabling delayed provider selection");
                    if (n == 0) {
                        Cipher.debug.println("Further warnings of this type will be suppressed");
                    }
                    new Exception("Call trace").printStackTrace();
                }
            }
            Throwable t = null;
            while (this.firstService != null || this.serviceIterator.hasNext()) {
                Provider.Service firstService;
                CipherSpi firstSpi;
                if (this.firstService != null) {
                    firstService = this.firstService;
                    firstSpi = this.firstSpi;
                    this.firstService = null;
                    this.firstSpi = null;
                }
                else {
                    firstService = this.serviceIterator.next();
                    firstSpi = null;
                }
                if (!JceSecurity.canUseProvider(firstService.getProvider())) {
                    continue;
                }
                final Transform transform = getTransform(firstService, this.transforms);
                if (transform == null) {
                    continue;
                }
                if (transform.supportsModePadding(firstService) == 0) {
                    continue;
                }
                try {
                    if (firstSpi == null) {
                        final Object instance = firstService.newInstance(null);
                        if (!(instance instanceof CipherSpi)) {
                            continue;
                        }
                        firstSpi = (CipherSpi)instance;
                    }
                    transform.setModePadding(firstSpi);
                    this.initCryptoPermission();
                    this.spi = firstSpi;
                    this.provider = firstService.getProvider();
                    this.firstService = null;
                    this.serviceIterator = null;
                    this.transforms = null;
                    return;
                }
                catch (Exception ex) {
                    t = ex;
                    continue;
                }
                break;
            }
            final ProviderException ex2 = new ProviderException("Could not construct CipherSpi instance");
            if (t != null) {
                ex2.initCause(t);
            }
            throw ex2;
        }
    }
    
    private void implInit(final CipherSpi cipherSpi, final int n, final int n2, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final AlgorithmParameters algorithmParameters, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        switch (n) {
            case 1: {
                this.checkCryptoPerm(cipherSpi, key);
                cipherSpi.engineInit(n2, key, secureRandom);
                break;
            }
            case 2: {
                this.checkCryptoPerm(cipherSpi, key, algorithmParameterSpec);
                cipherSpi.engineInit(n2, key, algorithmParameterSpec, secureRandom);
                break;
            }
            case 3: {
                this.checkCryptoPerm(cipherSpi, key, algorithmParameters);
                cipherSpi.engineInit(n2, key, algorithmParameters, secureRandom);
                break;
            }
            case 4: {
                this.checkCryptoPerm(cipherSpi, key);
                cipherSpi.engineInit(n2, key, secureRandom);
                break;
            }
            default: {
                throw new AssertionError((Object)("Internal Cipher error: " + n));
            }
        }
    }
    
    private void chooseProvider(final int n, final int n2, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final AlgorithmParameters algorithmParameters, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        synchronized (this.lock) {
            if (this.spi != null) {
                this.implInit(this.spi, n, n2, key, algorithmParameterSpec, algorithmParameters, secureRandom);
                return;
            }
            Throwable t = null;
            while (this.firstService != null || this.serviceIterator.hasNext()) {
                Provider.Service firstService;
                CipherSpi firstSpi;
                if (this.firstService != null) {
                    firstService = this.firstService;
                    firstSpi = this.firstSpi;
                    this.firstService = null;
                    this.firstSpi = null;
                }
                else {
                    firstService = this.serviceIterator.next();
                    firstSpi = null;
                }
                if (!firstService.supportsParameter(key)) {
                    continue;
                }
                if (!JceSecurity.canUseProvider(firstService.getProvider())) {
                    continue;
                }
                final Transform transform = getTransform(firstService, this.transforms);
                if (transform == null) {
                    continue;
                }
                if (transform.supportsModePadding(firstService) == 0) {
                    continue;
                }
                try {
                    if (firstSpi == null) {
                        firstSpi = (CipherSpi)firstService.newInstance(null);
                    }
                    transform.setModePadding(firstSpi);
                    this.initCryptoPermission();
                    this.implInit(firstSpi, n, n2, key, algorithmParameterSpec, algorithmParameters, secureRandom);
                    this.provider = firstService.getProvider();
                    this.spi = firstSpi;
                    this.firstService = null;
                    this.serviceIterator = null;
                    this.transforms = null;
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
    
    public final String getAlgorithm() {
        return this.transformation;
    }
    
    public final int getBlockSize() {
        this.chooseFirstProvider();
        return this.spi.engineGetBlockSize();
    }
    
    public final int getOutputSize(final int n) {
        if (!this.initialized && !(this instanceof NullCipher)) {
            throw new IllegalStateException("Cipher not initialized");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Input size must be equal to or greater than zero");
        }
        this.chooseFirstProvider();
        return this.spi.engineGetOutputSize(n);
    }
    
    public final byte[] getIV() {
        this.chooseFirstProvider();
        return this.spi.engineGetIV();
    }
    
    public final AlgorithmParameters getParameters() {
        this.chooseFirstProvider();
        return this.spi.engineGetParameters();
    }
    
    public final ExemptionMechanism getExemptionMechanism() {
        this.chooseFirstProvider();
        return this.exmech;
    }
    
    private void checkCryptoPerm(final CipherSpi cipherSpi, final Key key) throws InvalidKeyException {
        if (this.cryptoPerm == CryptoAllPermission.INSTANCE) {
            return;
        }
        AlgorithmParameterSpec algorithmParameterSpec;
        try {
            algorithmParameterSpec = this.getAlgorithmParameterSpec(cipherSpi.engineGetParameters());
        }
        catch (InvalidParameterSpecException ex) {
            throw new InvalidKeyException("Unsupported default algorithm parameters");
        }
        if (!this.passCryptoPermCheck(cipherSpi, key, algorithmParameterSpec)) {
            throw new InvalidKeyException("Illegal key size or default parameters");
        }
    }
    
    private void checkCryptoPerm(final CipherSpi cipherSpi, final Key key, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.cryptoPerm == CryptoAllPermission.INSTANCE) {
            return;
        }
        if (!this.passCryptoPermCheck(cipherSpi, key, null)) {
            throw new InvalidKeyException("Illegal key size");
        }
        if (algorithmParameterSpec != null && !this.passCryptoPermCheck(cipherSpi, key, algorithmParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Illegal parameters");
        }
    }
    
    private void checkCryptoPerm(final CipherSpi cipherSpi, final Key key, final AlgorithmParameters algorithmParameters) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.cryptoPerm == CryptoAllPermission.INSTANCE) {
            return;
        }
        AlgorithmParameterSpec algorithmParameterSpec;
        try {
            algorithmParameterSpec = this.getAlgorithmParameterSpec(algorithmParameters);
        }
        catch (InvalidParameterSpecException ex) {
            throw new InvalidAlgorithmParameterException("Failed to retrieve algorithm parameter specification");
        }
        this.checkCryptoPerm(cipherSpi, key, algorithmParameterSpec);
    }
    
    private boolean passCryptoPermCheck(final CipherSpi cipherSpi, final Key key, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException {
        final String exemptionMechanism = this.cryptoPerm.getExemptionMechanism();
        final int engineGetKeySize = cipherSpi.engineGetKeySize(key);
        final int index = this.transformation.indexOf(47);
        String s;
        if (index != -1) {
            s = this.transformation.substring(0, index);
        }
        else {
            s = this.transformation;
        }
        final CryptoPermission cryptoPermission = new CryptoPermission(s, engineGetKeySize, algorithmParameterSpec, exemptionMechanism);
        if (!this.cryptoPerm.implies(cryptoPermission)) {
            if (Cipher.debug != null) {
                Cipher.debug.println("Crypto Permission check failed");
                Cipher.debug.println("granted: " + this.cryptoPerm);
                Cipher.debug.println("requesting: " + cryptoPermission);
            }
            return false;
        }
        if (this.exmech == null) {
            return true;
        }
        try {
            if (!this.exmech.isCryptoAllowed(key)) {
                if (Cipher.debug != null) {
                    Cipher.debug.println(this.exmech.getName() + " isn't enforced");
                }
                return false;
            }
        }
        catch (ExemptionMechanismException ex) {
            if (Cipher.debug != null) {
                Cipher.debug.println("Cannot determine whether " + this.exmech.getName() + " has been enforced");
                ex.printStackTrace();
            }
            return false;
        }
        return true;
    }
    
    private static void checkOpmode(final int n) {
        if (n < 1 || n > 4) {
            throw new InvalidParameterException("Invalid operation mode");
        }
    }
    
    private static String getOpmodeString(final int n) {
        switch (n) {
            case 1: {
                return "encryption";
            }
            case 2: {
                return "decryption";
            }
            case 3: {
                return "key wrapping";
            }
            case 4: {
                return "key unwrapping";
            }
            default: {
                return "";
            }
        }
    }
    
    public final void init(final int n, final Key key) throws InvalidKeyException {
        this.init(n, key, JceSecurity.RANDOM);
    }
    
    public final void init(final int opmode, final Key key, final SecureRandom secureRandom) throws InvalidKeyException {
        this.initialized = false;
        checkOpmode(opmode);
        if (this.spi != null) {
            this.checkCryptoPerm(this.spi, key);
            this.spi.engineInit(opmode, key, secureRandom);
        }
        else {
            try {
                this.chooseProvider(1, opmode, key, null, null, secureRandom);
            }
            catch (InvalidAlgorithmParameterException ex) {
                throw new InvalidKeyException(ex);
            }
        }
        this.initialized = true;
        this.opmode = opmode;
        if (!Cipher.skipDebug && Cipher.pdebug != null) {
            Cipher.pdebug.println("Cipher." + this.transformation + " " + getOpmodeString(opmode) + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final void init(final int n, final Key key, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.init(n, key, algorithmParameterSpec, JceSecurity.RANDOM);
    }
    
    public final void init(final int opmode, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.initialized = false;
        checkOpmode(opmode);
        if (this.spi != null) {
            this.checkCryptoPerm(this.spi, key, algorithmParameterSpec);
            this.spi.engineInit(opmode, key, algorithmParameterSpec, secureRandom);
        }
        else {
            this.chooseProvider(2, opmode, key, algorithmParameterSpec, null, secureRandom);
        }
        this.initialized = true;
        this.opmode = opmode;
        if (!Cipher.skipDebug && Cipher.pdebug != null) {
            Cipher.pdebug.println("Cipher." + this.transformation + " " + getOpmodeString(opmode) + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final void init(final int n, final Key key, final AlgorithmParameters algorithmParameters) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.init(n, key, algorithmParameters, JceSecurity.RANDOM);
    }
    
    public final void init(final int opmode, final Key key, final AlgorithmParameters algorithmParameters, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.initialized = false;
        checkOpmode(opmode);
        if (this.spi != null) {
            this.checkCryptoPerm(this.spi, key, algorithmParameters);
            this.spi.engineInit(opmode, key, algorithmParameters, secureRandom);
        }
        else {
            this.chooseProvider(3, opmode, key, null, algorithmParameters, secureRandom);
        }
        this.initialized = true;
        this.opmode = opmode;
        if (!Cipher.skipDebug && Cipher.pdebug != null) {
            Cipher.pdebug.println("Cipher." + this.transformation + " " + getOpmodeString(opmode) + " algorithm from: " + this.provider.getName());
        }
    }
    
    public final void init(final int n, final Certificate certificate) throws InvalidKeyException {
        this.init(n, certificate, JceSecurity.RANDOM);
    }
    
    public final void init(final int opmode, final Certificate certificate, final SecureRandom secureRandom) throws InvalidKeyException {
        this.initialized = false;
        checkOpmode(opmode);
        if (certificate instanceof X509Certificate) {
            final X509Certificate x509Certificate = (X509Certificate)certificate;
            final Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null && !criticalExtensionOIDs.isEmpty() && criticalExtensionOIDs.contains("2.5.29.15")) {
                final boolean[] keyUsage = x509Certificate.getKeyUsage();
                if (keyUsage != null && ((opmode == 1 && keyUsage.length > 3 && !keyUsage[3]) || (opmode == 3 && keyUsage.length > 2 && !keyUsage[2]))) {
                    throw new InvalidKeyException("Wrong key usage");
                }
            }
        }
        final PublicKey publicKey = (certificate == null) ? null : certificate.getPublicKey();
        if (this.spi != null) {
            this.checkCryptoPerm(this.spi, publicKey);
            this.spi.engineInit(opmode, publicKey, secureRandom);
        }
        else {
            try {
                this.chooseProvider(4, opmode, publicKey, null, null, secureRandom);
            }
            catch (InvalidAlgorithmParameterException ex) {
                throw new InvalidKeyException(ex);
            }
        }
        this.initialized = true;
        this.opmode = opmode;
        if (!Cipher.skipDebug && Cipher.pdebug != null) {
            Cipher.pdebug.println("Cipher." + this.transformation + " " + getOpmodeString(opmode) + " algorithm from: " + this.provider.getName());
        }
    }
    
    private void checkCipherState() {
        if (!(this instanceof NullCipher)) {
            if (!this.initialized) {
                throw new IllegalStateException("Cipher not initialized");
            }
            if (this.opmode != 1 && this.opmode != 2) {
                throw new IllegalStateException("Cipher not initialized for encryption/decryption");
            }
        }
    }
    
    public final byte[] update(final byte[] array) {
        this.checkCipherState();
        if (array == null) {
            throw new IllegalArgumentException("Null input buffer");
        }
        this.chooseFirstProvider();
        if (array.length == 0) {
            return null;
        }
        return this.spi.engineUpdate(array, 0, array.length);
    }
    
    public final byte[] update(final byte[] array, final int n, final int n2) {
        this.checkCipherState();
        if (array == null || n < 0 || n2 > array.length - n || n2 < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        if (n2 == 0) {
            return null;
        }
        return this.spi.engineUpdate(array, n, n2);
    }
    
    public final int update(final byte[] array, final int n, final int n2, final byte[] array2) throws ShortBufferException {
        this.checkCipherState();
        if (array == null || n < 0 || n2 > array.length - n || n2 < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        if (n2 == 0) {
            return 0;
        }
        return this.spi.engineUpdate(array, n, n2, array2, 0);
    }
    
    public final int update(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws ShortBufferException {
        this.checkCipherState();
        if (array == null || n < 0 || n2 > array.length - n || n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        if (n2 == 0) {
            return 0;
        }
        return this.spi.engineUpdate(array, n, n2, array2, n3);
    }
    
    public final int update(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws ShortBufferException {
        this.checkCipherState();
        if (byteBuffer == null || byteBuffer2 == null) {
            throw new IllegalArgumentException("Buffers must not be null");
        }
        if (byteBuffer == byteBuffer2) {
            throw new IllegalArgumentException("Input and output buffers must not be the same object, consider using buffer.duplicate()");
        }
        if (byteBuffer2.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        this.chooseFirstProvider();
        return this.spi.engineUpdate(byteBuffer, byteBuffer2);
    }
    
    public final byte[] doFinal() throws IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(null, 0, 0);
    }
    
    public final int doFinal(final byte[] array, final int n) throws IllegalBlockSizeException, ShortBufferException, BadPaddingException {
        this.checkCipherState();
        if (array == null || n < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(null, 0, 0, array, n);
    }
    
    public final byte[] doFinal(final byte[] array) throws IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (array == null) {
            throw new IllegalArgumentException("Null input buffer");
        }
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(array, 0, array.length);
    }
    
    public final byte[] doFinal(final byte[] array, final int n, final int n2) throws IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (array == null || n < 0 || n2 > array.length - n || n2 < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(array, n, n2);
    }
    
    public final int doFinal(final byte[] array, final int n, final int n2, final byte[] array2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (array == null || n < 0 || n2 > array.length - n || n2 < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(array, n, n2, array2, 0);
    }
    
    public final int doFinal(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (array == null || n < 0 || n2 > array.length - n || n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(array, n, n2, array2, n3);
    }
    
    public final int doFinal(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (byteBuffer == null || byteBuffer2 == null) {
            throw new IllegalArgumentException("Buffers must not be null");
        }
        if (byteBuffer == byteBuffer2) {
            throw new IllegalArgumentException("Input and output buffers must not be the same object, consider using buffer.duplicate()");
        }
        if (byteBuffer2.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        this.chooseFirstProvider();
        return this.spi.engineDoFinal(byteBuffer, byteBuffer2);
    }
    
    public final byte[] wrap(final Key key) throws IllegalBlockSizeException, InvalidKeyException {
        if (!(this instanceof NullCipher)) {
            if (!this.initialized) {
                throw new IllegalStateException("Cipher not initialized");
            }
            if (this.opmode != 3) {
                throw new IllegalStateException("Cipher not initialized for wrapping keys");
            }
        }
        this.chooseFirstProvider();
        return this.spi.engineWrap(key);
    }
    
    public final Key unwrap(final byte[] array, final String s, final int n) throws InvalidKeyException, NoSuchAlgorithmException {
        if (!(this instanceof NullCipher)) {
            if (!this.initialized) {
                throw new IllegalStateException("Cipher not initialized");
            }
            if (this.opmode != 4) {
                throw new IllegalStateException("Cipher not initialized for unwrapping keys");
            }
        }
        if (n != 3 && n != 2 && n != 1) {
            throw new InvalidParameterException("Invalid key type");
        }
        this.chooseFirstProvider();
        return this.spi.engineUnwrap(array, s, n);
    }
    
    private AlgorithmParameterSpec getAlgorithmParameterSpec(final AlgorithmParameters algorithmParameters) throws InvalidParameterSpecException {
        if (algorithmParameters == null) {
            return null;
        }
        final String upperCase = algorithmParameters.getAlgorithm().toUpperCase(Locale.ENGLISH);
        if (upperCase.equalsIgnoreCase("RC2")) {
            return algorithmParameters.getParameterSpec(RC2ParameterSpec.class);
        }
        if (upperCase.equalsIgnoreCase("RC5")) {
            return algorithmParameters.getParameterSpec(RC5ParameterSpec.class);
        }
        if (upperCase.startsWith("PBE")) {
            return algorithmParameters.getParameterSpec(PBEParameterSpec.class);
        }
        if (upperCase.startsWith("DES")) {
            return algorithmParameters.getParameterSpec(IvParameterSpec.class);
        }
        return null;
    }
    
    private static CryptoPermission getConfiguredPermission(final String s) throws NullPointerException, NoSuchAlgorithmException {
        if (s == null) {
            throw new NullPointerException();
        }
        return JceSecurityManager.INSTANCE.getCryptoPermission(tokenizeTransformation(s)[0]);
    }
    
    public static final int getMaxAllowedKeyLength(final String s) throws NoSuchAlgorithmException {
        return getConfiguredPermission(s).getMaxKeySize();
    }
    
    public static final AlgorithmParameterSpec getMaxAllowedParameterSpec(final String s) throws NoSuchAlgorithmException {
        return getConfiguredPermission(s).getAlgorithmParameterSpec();
    }
    
    public final void updateAAD(final byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("src buffer is null");
        }
        this.updateAAD(array, 0, array.length);
    }
    
    public final void updateAAD(final byte[] array, final int n, final int n2) {
        this.checkCipherState();
        if (array == null || n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IllegalArgumentException("Bad arguments");
        }
        this.chooseFirstProvider();
        if (n2 == 0) {
            return;
        }
        this.spi.engineUpdateAAD(array, n, n2);
    }
    
    public final void updateAAD(final ByteBuffer byteBuffer) {
        this.checkCipherState();
        if (byteBuffer == null) {
            throw new IllegalArgumentException("src ByteBuffer is null");
        }
        this.chooseFirstProvider();
        if (byteBuffer.remaining() == 0) {
            return;
        }
        this.spi.engineUpdateAAD(byteBuffer);
    }
    
    static {
        debug = Debug.getInstance("jca", "Cipher");
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("cipher"));
        Cipher.warnCount = 10;
    }
    
    private static class Transform
    {
        final String transform;
        final String suffix;
        final String mode;
        final String pad;
        private static final ConcurrentMap<String, Pattern> patternCache;
        
        Transform(final String s, final String s2, final String mode, final String pad) {
            this.transform = s + s2;
            this.suffix = s2.toUpperCase(Locale.ENGLISH);
            this.mode = mode;
            this.pad = pad;
        }
        
        void setModePadding(final CipherSpi cipherSpi) throws NoSuchAlgorithmException, NoSuchPaddingException {
            if (this.mode != null) {
                cipherSpi.engineSetMode(this.mode);
            }
            if (this.pad != null) {
                cipherSpi.engineSetPadding(this.pad);
            }
        }
        
        int supportsModePadding(final Provider.Service service) {
            final int supportsMode = this.supportsMode(service);
            if (supportsMode == 0) {
                return supportsMode;
            }
            return Math.min(supportsMode, this.supportsPadding(service));
        }
        
        int supportsMode(final Provider.Service service) {
            return supports(service, "SupportedModes", this.mode);
        }
        
        int supportsPadding(final Provider.Service service) {
            return supports(service, "SupportedPaddings", this.pad);
        }
        
        private static int supports(final Provider.Service service, final String s, final String s2) {
            if (s2 == null) {
                return 2;
            }
            final String attribute = service.getAttribute(s);
            if (attribute == null) {
                return 1;
            }
            return matches(attribute, s2) ? 2 : 0;
        }
        
        private static boolean matches(final String s, final String s2) {
            Pattern compile = Transform.patternCache.get(s);
            if (compile == null) {
                compile = Pattern.compile(s);
                Transform.patternCache.putIfAbsent(s, compile);
            }
            return compile.matcher(s2.toUpperCase(Locale.ENGLISH)).matches();
        }
        
        static {
            patternCache = new ConcurrentHashMap<String, Pattern>();
        }
    }
}
