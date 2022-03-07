package java.security;

import sun.security.util.*;
import sun.security.jca.*;
import java.security.spec.*;
import java.security.cert.*;
import java.nio.*;
import sun.misc.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import javax.crypto.*;

public abstract class Signature extends SignatureSpi
{
    private static final Debug debug;
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private String algorithm;
    Provider provider;
    protected static final int UNINITIALIZED = 0;
    protected static final int SIGN = 2;
    protected static final int VERIFY = 3;
    protected int state;
    private static final String RSA_SIGNATURE = "NONEwithRSA";
    private static final String RSA_CIPHER = "RSA/ECB/PKCS1Padding";
    private static final List<ServiceId> rsaIds;
    private static final Map<String, Boolean> signatureInfo;
    
    protected Signature(final String algorithm) {
        this.state = 0;
        this.algorithm = algorithm;
    }
    
    public static Signature getInstance(final String s) throws NoSuchAlgorithmException {
        List<Provider.Service> list;
        if (s.equalsIgnoreCase("NONEwithRSA")) {
            list = GetInstance.getServices(Signature.rsaIds);
        }
        else {
            list = GetInstance.getServices("Signature", s);
        }
        final Iterator<Provider.Service> iterator = list.iterator();
        if (!iterator.hasNext()) {
            throw new NoSuchAlgorithmException(s + " Signature not available");
        }
        Provider.Service service;
        while (true) {
            service = iterator.next();
            if (isSpi(service)) {
                break;
            }
            try {
                return getInstance(GetInstance.getInstance(service, SignatureSpi.class), s);
            }
            catch (NoSuchAlgorithmException ex2) {
                final NoSuchAlgorithmException ex = ex2;
                if (!iterator.hasNext()) {
                    throw ex;
                }
                continue;
            }
        }
        return new Delegate(service, iterator, s);
    }
    
    private static Signature getInstance(final GetInstance.Instance instance, final String algorithm) {
        Signature signature;
        if (instance.impl instanceof Signature) {
            signature = (Signature)instance.impl;
            signature.algorithm = algorithm;
        }
        else {
            signature = new Delegate((SignatureSpi)instance.impl, algorithm);
        }
        signature.provider = instance.provider;
        return signature;
    }
    
    private static boolean isSpi(final Provider.Service service) {
        if (service.getType().equals("Cipher")) {
            return true;
        }
        final String className = service.getClassName();
        Boolean value = Signature.signatureInfo.get(className);
        if (value == null) {
            try {
                final Object instance = service.newInstance(null);
                final boolean b = instance instanceof SignatureSpi && !(instance instanceof Signature);
                if (Signature.debug != null && !b) {
                    Signature.debug.println("Not a SignatureSpi " + className);
                    Signature.debug.println("Delayed provider selection may not be available for algorithm " + service.getAlgorithm());
                }
                value = b;
                Signature.signatureInfo.put(className, value);
            }
            catch (Exception ex) {
                return false;
            }
        }
        return value;
    }
    
    public static Signature getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (!s.equalsIgnoreCase("NONEwithRSA")) {
            return getInstance(GetInstance.getInstance("Signature", SignatureSpi.class, s, s2), s);
        }
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }
        final Provider provider = Security.getProvider(s2);
        if (provider == null) {
            throw new NoSuchProviderException("no such provider: " + s2);
        }
        return getInstanceRSA(provider);
    }
    
    public static Signature getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        if (!s.equalsIgnoreCase("NONEwithRSA")) {
            return getInstance(GetInstance.getInstance("Signature", SignatureSpi.class, s, provider), s);
        }
        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }
        return getInstanceRSA(provider);
    }
    
    private static Signature getInstanceRSA(final Provider provider) throws NoSuchAlgorithmException {
        final Provider.Service service = provider.getService("Signature", "NONEwithRSA");
        if (service != null) {
            return getInstance(GetInstance.getInstance(service, SignatureSpi.class), "NONEwithRSA");
        }
        try {
            return new Delegate(new CipherAdapter(Cipher.getInstance("RSA/ECB/PKCS1Padding", provider)), "NONEwithRSA");
        }
        catch (GeneralSecurityException ex) {
            throw new NoSuchAlgorithmException("no such algorithm: NONEwithRSA for provider " + provider.getName(), ex);
        }
    }
    
    public final Provider getProvider() {
        this.chooseFirstProvider();
        return this.provider;
    }
    
    private String getProviderName() {
        return (this.provider == null) ? "(no provider)" : this.provider.getName();
    }
    
    void chooseFirstProvider() {
    }
    
    public final void initVerify(final PublicKey publicKey) throws InvalidKeyException {
        this.engineInitVerify(publicKey);
        this.state = 3;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " verification algorithm from: " + this.getProviderName());
        }
    }
    
    final void initVerify(final PublicKey publicKey, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.engineInitVerify(publicKey, algorithmParameterSpec);
        this.state = 3;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " verification algorithm from: " + this.getProviderName());
        }
    }
    
    private static PublicKey getPublicKeyFromCert(final Certificate certificate) throws InvalidKeyException {
        if (certificate instanceof X509Certificate) {
            final X509Certificate x509Certificate = (X509Certificate)certificate;
            final Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null && !criticalExtensionOIDs.isEmpty() && criticalExtensionOIDs.contains("2.5.29.15")) {
                final boolean[] keyUsage = x509Certificate.getKeyUsage();
                if (keyUsage != null && !keyUsage[0]) {
                    throw new InvalidKeyException("Wrong key usage");
                }
            }
        }
        return certificate.getPublicKey();
    }
    
    public final void initVerify(final Certificate certificate) throws InvalidKeyException {
        this.engineInitVerify(getPublicKeyFromCert(certificate));
        this.state = 3;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " verification algorithm from: " + this.getProviderName());
        }
    }
    
    final void initVerify(final Certificate certificate, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.engineInitVerify(getPublicKeyFromCert(certificate), algorithmParameterSpec);
        this.state = 3;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " verification algorithm from: " + this.getProviderName());
        }
    }
    
    public final void initSign(final PrivateKey privateKey) throws InvalidKeyException {
        this.engineInitSign(privateKey);
        this.state = 2;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " signing algorithm from: " + this.getProviderName());
        }
    }
    
    public final void initSign(final PrivateKey privateKey, final SecureRandom secureRandom) throws InvalidKeyException {
        this.engineInitSign(privateKey, secureRandom);
        this.state = 2;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " signing algorithm from: " + this.getProviderName());
        }
    }
    
    final void initSign(final PrivateKey privateKey, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.engineInitSign(privateKey, algorithmParameterSpec, secureRandom);
        this.state = 2;
        if (!Signature.skipDebug && Signature.pdebug != null) {
            Signature.pdebug.println("Signature." + this.algorithm + " signing algorithm from: " + this.getProviderName());
        }
    }
    
    public final byte[] sign() throws SignatureException {
        if (this.state == 2) {
            return this.engineSign();
        }
        throw new SignatureException("object not initialized for signing");
    }
    
    public final int sign(final byte[] array, final int n, final int n2) throws SignatureException {
        if (array == null) {
            throw new IllegalArgumentException("No output buffer given");
        }
        if (n < 0 || n2 < 0) {
            throw new IllegalArgumentException("offset or len is less than 0");
        }
        if (array.length - n < n2) {
            throw new IllegalArgumentException("Output buffer too small for specified offset and length");
        }
        if (this.state != 2) {
            throw new SignatureException("object not initialized for signing");
        }
        return this.engineSign(array, n, n2);
    }
    
    public final boolean verify(final byte[] array) throws SignatureException {
        if (this.state == 3) {
            return this.engineVerify(array);
        }
        throw new SignatureException("object not initialized for verification");
    }
    
    public final boolean verify(final byte[] array, final int n, final int n2) throws SignatureException {
        if (this.state != 3) {
            throw new SignatureException("object not initialized for verification");
        }
        if (array == null) {
            throw new IllegalArgumentException("signature is null");
        }
        if (n < 0 || n2 < 0) {
            throw new IllegalArgumentException("offset or length is less than 0");
        }
        if (array.length - n < n2) {
            throw new IllegalArgumentException("signature too small for specified offset and length");
        }
        return this.engineVerify(array, n, n2);
    }
    
    public final void update(final byte b) throws SignatureException {
        if (this.state == 3 || this.state == 2) {
            this.engineUpdate(b);
            return;
        }
        throw new SignatureException("object not initialized for signature or verification");
    }
    
    public final void update(final byte[] array) throws SignatureException {
        this.update(array, 0, array.length);
    }
    
    public final void update(final byte[] array, final int n, final int n2) throws SignatureException {
        if (this.state != 2 && this.state != 3) {
            throw new SignatureException("object not initialized for signature or verification");
        }
        if (array == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (n < 0 || n2 < 0) {
            throw new IllegalArgumentException("off or len is less than 0");
        }
        if (array.length - n < n2) {
            throw new IllegalArgumentException("data too small for specified offset and length");
        }
        this.engineUpdate(array, n, n2);
    }
    
    public final void update(final ByteBuffer byteBuffer) throws SignatureException {
        if (this.state != 2 && this.state != 3) {
            throw new SignatureException("object not initialized for signature or verification");
        }
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        this.engineUpdate(byteBuffer);
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    @Override
    public String toString() {
        String s = "";
        switch (this.state) {
            case 0: {
                s = "<not initialized>";
                break;
            }
            case 3: {
                s = "<initialized for verifying>";
                break;
            }
            case 2: {
                s = "<initialized for signing>";
                break;
            }
        }
        return "Signature object: " + this.getAlgorithm() + s;
    }
    
    @Deprecated
    public final void setParameter(final String s, final Object o) throws InvalidParameterException {
        this.engineSetParameter(s, o);
    }
    
    public final void setParameter(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.engineSetParameter(algorithmParameterSpec);
    }
    
    public final AlgorithmParameters getParameters() {
        return this.engineGetParameters();
    }
    
    @Deprecated
    public final Object getParameter(final String s) throws InvalidParameterException {
        return this.engineGetParameter(s);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }
    
    static {
        SharedSecrets.setJavaSecuritySignatureAccess(new JavaSecuritySignatureAccess() {
            @Override
            public void initVerify(final Signature signature, final PublicKey publicKey, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
                signature.initVerify(publicKey, algorithmParameterSpec);
            }
            
            @Override
            public void initVerify(final Signature signature, final Certificate certificate, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
                signature.initVerify(certificate, algorithmParameterSpec);
            }
            
            @Override
            public void initSign(final Signature signature, final PrivateKey privateKey, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
                signature.initSign(privateKey, algorithmParameterSpec, secureRandom);
            }
        });
        debug = Debug.getInstance("jca", "Signature");
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("signature"));
        rsaIds = Arrays.asList(new ServiceId("Signature", "NONEwithRSA"), new ServiceId("Cipher", "RSA/ECB/PKCS1Padding"), new ServiceId("Cipher", "RSA/ECB"), new ServiceId("Cipher", "RSA//PKCS1Padding"), new ServiceId("Cipher", "RSA"));
        signatureInfo = new ConcurrentHashMap<String, Boolean>();
        final Boolean true = Boolean.TRUE;
        Signature.signatureInfo.put("sun.security.provider.DSA$RawDSA", true);
        Signature.signatureInfo.put("sun.security.provider.DSA$SHA1withDSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSASignature$MD2withRSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSASignature$MD5withRSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSASignature$SHA1withRSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSASignature$SHA256withRSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSASignature$SHA384withRSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSASignature$SHA512withRSA", true);
        Signature.signatureInfo.put("sun.security.rsa.RSAPSSSignature", true);
        Signature.signatureInfo.put("com.sun.net.ssl.internal.ssl.RSASignature", true);
        Signature.signatureInfo.put("sun.security.pkcs11.P11Signature", true);
    }
    
    private static class CipherAdapter extends SignatureSpi
    {
        private final Cipher cipher;
        private ByteArrayOutputStream data;
        
        CipherAdapter(final Cipher cipher) {
            this.cipher = cipher;
        }
        
        @Override
        protected void engineInitVerify(final PublicKey publicKey) throws InvalidKeyException {
            this.cipher.init(2, publicKey);
            if (this.data == null) {
                this.data = new ByteArrayOutputStream(128);
            }
            else {
                this.data.reset();
            }
        }
        
        @Override
        protected void engineInitSign(final PrivateKey privateKey) throws InvalidKeyException {
            this.cipher.init(1, privateKey);
            this.data = null;
        }
        
        @Override
        protected void engineInitSign(final PrivateKey privateKey, final SecureRandom secureRandom) throws InvalidKeyException {
            this.cipher.init(1, privateKey, secureRandom);
            this.data = null;
        }
        
        @Override
        protected void engineUpdate(final byte b) throws SignatureException {
            this.engineUpdate(new byte[] { b }, 0, 1);
        }
        
        @Override
        protected void engineUpdate(final byte[] array, final int n, final int n2) throws SignatureException {
            if (this.data != null) {
                this.data.write(array, n, n2);
                return;
            }
            final byte[] update = this.cipher.update(array, n, n2);
            if (update != null && update.length != 0) {
                throw new SignatureException("Cipher unexpectedly returned data");
            }
        }
        
        @Override
        protected byte[] engineSign() throws SignatureException {
            try {
                return this.cipher.doFinal();
            }
            catch (IllegalBlockSizeException ex) {
                throw new SignatureException("doFinal() failed", ex);
            }
            catch (BadPaddingException ex2) {
                throw new SignatureException("doFinal() failed", ex2);
            }
        }
        
        @Override
        protected boolean engineVerify(final byte[] array) throws SignatureException {
            try {
                final byte[] doFinal = this.cipher.doFinal(array);
                final byte[] byteArray = this.data.toByteArray();
                this.data.reset();
                return MessageDigest.isEqual(doFinal, byteArray);
            }
            catch (BadPaddingException ex2) {
                return false;
            }
            catch (IllegalBlockSizeException ex) {
                throw new SignatureException("doFinal() failed", ex);
            }
        }
        
        @Override
        protected void engineSetParameter(final String s, final Object o) throws InvalidParameterException {
            throw new InvalidParameterException("Parameters not supported");
        }
        
        @Override
        protected Object engineGetParameter(final String s) throws InvalidParameterException {
            throw new InvalidParameterException("Parameters not supported");
        }
    }
    
    private static class Delegate extends Signature
    {
        private SignatureSpi sigSpi;
        private final Object lock;
        private Provider.Service firstService;
        private Iterator<Provider.Service> serviceIterator;
        private static int warnCount;
        private static final int I_PUB = 1;
        private static final int I_PRIV = 2;
        private static final int I_PRIV_SR = 3;
        private static final int I_PUB_PARAM = 4;
        private static final int I_PRIV_PARAM_SR = 5;
        private static final int S_PARAM = 6;
        
        Delegate(final SignatureSpi sigSpi, final String s) {
            super(s);
            this.sigSpi = sigSpi;
            this.lock = null;
        }
        
        Delegate(final Provider.Service firstService, final Iterator<Provider.Service> serviceIterator, final String s) {
            super(s);
            this.firstService = firstService;
            this.serviceIterator = serviceIterator;
            this.lock = new Object();
        }
        
        @Override
        public Object clone() throws CloneNotSupportedException {
            this.chooseFirstProvider();
            if (this.sigSpi instanceof Cloneable) {
                final Delegate delegate = new Delegate((SignatureSpi)this.sigSpi.clone(), this.algorithm);
                delegate.provider = super.provider;
                return delegate;
            }
            throw new CloneNotSupportedException();
        }
        
        private static SignatureSpi newInstance(final Provider.Service service) throws NoSuchAlgorithmException {
            if (service.getType().equals("Cipher")) {
                try {
                    return new CipherAdapter(Cipher.getInstance("RSA/ECB/PKCS1Padding", service.getProvider()));
                }
                catch (NoSuchPaddingException ex) {
                    throw new NoSuchAlgorithmException(ex);
                }
            }
            final Object instance = service.newInstance(null);
            if (!(instance instanceof SignatureSpi)) {
                throw new NoSuchAlgorithmException("Not a SignatureSpi: " + ((SignatureSpi)instance).getClass().getName());
            }
            return (SignatureSpi)instance;
        }
        
        @Override
        void chooseFirstProvider() {
            if (this.sigSpi != null) {
                return;
            }
            synchronized (this.lock) {
                if (this.sigSpi != null) {
                    return;
                }
                if (Signature.debug != null) {
                    final int n = --Delegate.warnCount;
                    if (n >= 0) {
                        Signature.debug.println("Signature.init() not first method called, disabling delayed provider selection");
                        if (n == 0) {
                            Signature.debug.println("Further warnings of this type will be suppressed");
                        }
                        new Exception("Debug call trace").printStackTrace();
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
                    if (!isSpi(firstService)) {
                        continue;
                    }
                    try {
                        this.sigSpi = newInstance(firstService);
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
                final ProviderException ex2 = new ProviderException("Could not construct SignatureSpi instance");
                if (t != null) {
                    ex2.initCause(t);
                }
                throw ex2;
            }
        }
        
        private void chooseProvider(final int n, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
            synchronized (this.lock) {
                if (this.sigSpi != null) {
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
                    if (key != null && !firstService.supportsParameter(key)) {
                        continue;
                    }
                    if (!isSpi(firstService)) {
                        continue;
                    }
                    try {
                        final SignatureSpi instance = newInstance(firstService);
                        this.tryOperation(instance, n, key, algorithmParameterSpec, secureRandom);
                        this.provider = firstService.getProvider();
                        this.sigSpi = instance;
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
                if (t instanceof RuntimeException) {
                    throw (RuntimeException)t;
                }
                if (t instanceof InvalidAlgorithmParameterException) {
                    throw (InvalidAlgorithmParameterException)t;
                }
                throw new InvalidKeyException("No installed provider supports this key: " + ((key != null) ? key.getClass().getName() : "(null)"), t);
            }
        }
        
        private void tryOperation(final SignatureSpi signatureSpi, final int n, final Key key, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
            switch (n) {
                case 1: {
                    signatureSpi.engineInitVerify((PublicKey)key);
                    break;
                }
                case 4: {
                    signatureSpi.engineInitVerify((PublicKey)key, algorithmParameterSpec);
                    break;
                }
                case 2: {
                    signatureSpi.engineInitSign((PrivateKey)key);
                    break;
                }
                case 3: {
                    signatureSpi.engineInitSign((PrivateKey)key, secureRandom);
                    break;
                }
                case 5: {
                    signatureSpi.engineInitSign((PrivateKey)key, algorithmParameterSpec, secureRandom);
                    break;
                }
                case 6: {
                    signatureSpi.engineSetParameter(algorithmParameterSpec);
                    break;
                }
                default: {
                    throw new AssertionError((Object)("Internal error: " + n));
                }
            }
        }
        
        @Override
        protected void engineInitVerify(final PublicKey publicKey) throws InvalidKeyException {
            if (this.sigSpi != null) {
                this.sigSpi.engineInitVerify(publicKey);
            }
            else {
                try {
                    this.chooseProvider(1, publicKey, null, null);
                }
                catch (InvalidAlgorithmParameterException ex) {
                    throw new InvalidKeyException(ex);
                }
            }
        }
        
        @Override
        void engineInitVerify(final PublicKey publicKey, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
            if (this.sigSpi != null) {
                this.sigSpi.engineInitVerify(publicKey, algorithmParameterSpec);
            }
            else {
                this.chooseProvider(4, publicKey, algorithmParameterSpec, null);
            }
        }
        
        @Override
        protected void engineInitSign(final PrivateKey privateKey) throws InvalidKeyException {
            if (this.sigSpi != null) {
                this.sigSpi.engineInitSign(privateKey);
            }
            else {
                try {
                    this.chooseProvider(2, privateKey, null, null);
                }
                catch (InvalidAlgorithmParameterException ex) {
                    throw new InvalidKeyException(ex);
                }
            }
        }
        
        @Override
        protected void engineInitSign(final PrivateKey privateKey, final SecureRandom secureRandom) throws InvalidKeyException {
            if (this.sigSpi != null) {
                this.sigSpi.engineInitSign(privateKey, secureRandom);
            }
            else {
                try {
                    this.chooseProvider(3, privateKey, null, secureRandom);
                }
                catch (InvalidAlgorithmParameterException ex) {
                    throw new InvalidKeyException(ex);
                }
            }
        }
        
        @Override
        void engineInitSign(final PrivateKey privateKey, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
            if (this.sigSpi != null) {
                this.sigSpi.engineInitSign(privateKey, algorithmParameterSpec, secureRandom);
            }
            else {
                this.chooseProvider(5, privateKey, algorithmParameterSpec, secureRandom);
            }
        }
        
        @Override
        protected void engineUpdate(final byte b) throws SignatureException {
            this.chooseFirstProvider();
            this.sigSpi.engineUpdate(b);
        }
        
        @Override
        protected void engineUpdate(final byte[] array, final int n, final int n2) throws SignatureException {
            this.chooseFirstProvider();
            this.sigSpi.engineUpdate(array, n, n2);
        }
        
        @Override
        protected void engineUpdate(final ByteBuffer byteBuffer) {
            this.chooseFirstProvider();
            this.sigSpi.engineUpdate(byteBuffer);
        }
        
        @Override
        protected byte[] engineSign() throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineSign();
        }
        
        @Override
        protected int engineSign(final byte[] array, final int n, final int n2) throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineSign(array, n, n2);
        }
        
        @Override
        protected boolean engineVerify(final byte[] array) throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineVerify(array);
        }
        
        @Override
        protected boolean engineVerify(final byte[] array, final int n, final int n2) throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineVerify(array, n, n2);
        }
        
        @Override
        protected void engineSetParameter(final String s, final Object o) throws InvalidParameterException {
            this.chooseFirstProvider();
            this.sigSpi.engineSetParameter(s, o);
        }
        
        @Override
        protected void engineSetParameter(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (this.sigSpi != null) {
                this.sigSpi.engineSetParameter(algorithmParameterSpec);
            }
            else {
                try {
                    this.chooseProvider(6, null, algorithmParameterSpec, null);
                }
                catch (InvalidKeyException ex) {
                    throw new InvalidAlgorithmParameterException(ex);
                }
            }
        }
        
        @Override
        protected Object engineGetParameter(final String s) throws InvalidParameterException {
            this.chooseFirstProvider();
            return this.sigSpi.engineGetParameter(s);
        }
        
        @Override
        protected AlgorithmParameters engineGetParameters() {
            this.chooseFirstProvider();
            return this.sigSpi.engineGetParameters();
        }
        
        static {
            Delegate.warnCount = 10;
        }
    }
}
