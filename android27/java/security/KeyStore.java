package java.security;

import sun.security.util.*;
import java.io.*;
import javax.security.auth.callback.*;
import java.security.spec.*;
import javax.security.auth.*;
import java.security.cert.*;
import java.util.*;
import javax.crypto.*;

public class KeyStore
{
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private static final String KEYSTORE_TYPE = "keystore.type";
    private String type;
    private Provider provider;
    private KeyStoreSpi keyStoreSpi;
    private boolean initialized;
    
    protected KeyStore(final KeyStoreSpi keyStoreSpi, final Provider provider, final String type) {
        this.initialized = false;
        this.keyStoreSpi = keyStoreSpi;
        this.provider = provider;
        this.type = type;
        if (!KeyStore.skipDebug && KeyStore.pdebug != null) {
            KeyStore.pdebug.println("KeyStore." + type.toUpperCase() + " type from: " + this.provider.getName());
        }
    }
    
    public static KeyStore getInstance(final String s) throws KeyStoreException {
        try {
            final Object[] impl = Security.getImpl(s, "KeyStore", (String)null);
            return new KeyStore((KeyStoreSpi)impl[0], (Provider)impl[1], s);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new KeyStoreException(s + " not found", ex);
        }
        catch (NoSuchProviderException ex2) {
            throw new KeyStoreException(s + " not found", ex2);
        }
    }
    
    public static KeyStore getInstance(final String s, final String s2) throws KeyStoreException, NoSuchProviderException {
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }
        try {
            final Object[] impl = Security.getImpl(s, "KeyStore", s2);
            return new KeyStore((KeyStoreSpi)impl[0], (Provider)impl[1], s);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new KeyStoreException(s + " not found", ex);
        }
    }
    
    public static KeyStore getInstance(final String s, final Provider provider) throws KeyStoreException {
        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }
        try {
            final Object[] impl = Security.getImpl(s, "KeyStore", provider);
            return new KeyStore((KeyStoreSpi)impl[0], (Provider)impl[1], s);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new KeyStoreException(s + " not found", ex);
        }
    }
    
    public static final String getDefaultType() {
        String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("keystore.type");
            }
        });
        if (s == null) {
            s = "jks";
        }
        return s;
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final String getType() {
        return this.type;
    }
    
    public final Key getKey(final String s, final char[] array) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineGetKey(s, array);
    }
    
    public final Certificate[] getCertificateChain(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineGetCertificateChain(s);
    }
    
    public final Certificate getCertificate(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineGetCertificate(s);
    }
    
    public final Date getCreationDate(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineGetCreationDate(s);
    }
    
    public final void setKeyEntry(final String s, final Key key, final char[] array, final Certificate[] array2) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        if (key instanceof PrivateKey && (array2 == null || array2.length == 0)) {
            throw new IllegalArgumentException("Private key must be accompanied by certificate chain");
        }
        this.keyStoreSpi.engineSetKeyEntry(s, key, array, array2);
    }
    
    public final void setKeyEntry(final String s, final byte[] array, final Certificate[] array2) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        this.keyStoreSpi.engineSetKeyEntry(s, array, array2);
    }
    
    public final void setCertificateEntry(final String s, final Certificate certificate) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        this.keyStoreSpi.engineSetCertificateEntry(s, certificate);
    }
    
    public final void deleteEntry(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        this.keyStoreSpi.engineDeleteEntry(s);
    }
    
    public final Enumeration<String> aliases() throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineAliases();
    }
    
    public final boolean containsAlias(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineContainsAlias(s);
    }
    
    public final int size() throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineSize();
    }
    
    public final boolean isKeyEntry(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineIsKeyEntry(s);
    }
    
    public final boolean isCertificateEntry(final String s) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineIsCertificateEntry(s);
    }
    
    public final String getCertificateAlias(final Certificate certificate) throws KeyStoreException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineGetCertificateAlias(certificate);
    }
    
    public final void store(final OutputStream outputStream, final char[] array) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        this.keyStoreSpi.engineStore(outputStream, array);
    }
    
    public final void store(final LoadStoreParameter loadStoreParameter) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        this.keyStoreSpi.engineStore(loadStoreParameter);
    }
    
    public final void load(final InputStream inputStream, final char[] array) throws IOException, NoSuchAlgorithmException, CertificateException {
        this.keyStoreSpi.engineLoad(inputStream, array);
        this.initialized = true;
    }
    
    public final void load(final LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        this.keyStoreSpi.engineLoad(loadStoreParameter);
        this.initialized = true;
    }
    
    public final Entry getEntry(final String s, final ProtectionParameter protectionParameter) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
        if (s == null) {
            throw new NullPointerException("invalid null input");
        }
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineGetEntry(s, protectionParameter);
    }
    
    public final void setEntry(final String s, final Entry entry, final ProtectionParameter protectionParameter) throws KeyStoreException {
        if (s == null || entry == null) {
            throw new NullPointerException("invalid null input");
        }
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        this.keyStoreSpi.engineSetEntry(s, entry, protectionParameter);
    }
    
    public final boolean entryInstanceOf(final String s, final Class<? extends Entry> clazz) throws KeyStoreException {
        if (s == null || clazz == null) {
            throw new NullPointerException("invalid null input");
        }
        if (!this.initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return this.keyStoreSpi.engineEntryInstanceOf(s, clazz);
    }
    
    static {
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("keystore"));
    }
    
    public abstract static class Builder
    {
        static final int MAX_CALLBACK_TRIES = 3;
        
        public abstract KeyStore getKeyStore() throws KeyStoreException;
        
        public abstract ProtectionParameter getProtectionParameter(final String p0) throws KeyStoreException;
        
        public static Builder newInstance(final KeyStore keyStore, final ProtectionParameter protectionParameter) {
            if (keyStore == null || protectionParameter == null) {
                throw new NullPointerException();
            }
            if (!keyStore.initialized) {
                throw new IllegalArgumentException("KeyStore not initialized");
            }
            return new Builder() {
                private volatile boolean getCalled;
                
                @Override
                public KeyStore getKeyStore() {
                    this.getCalled = true;
                    return keyStore;
                }
                
                @Override
                public ProtectionParameter getProtectionParameter(final String s) {
                    if (s == null) {
                        throw new NullPointerException();
                    }
                    if (!this.getCalled) {
                        throw new IllegalStateException("getKeyStore() must be called first");
                    }
                    return protectionParameter;
                }
            };
        }
        
        public static Builder newInstance(final String s, final Provider provider, final File file, final ProtectionParameter protectionParameter) {
            if (s == null || file == null || protectionParameter == null) {
                throw new NullPointerException();
            }
            if (!(protectionParameter instanceof PasswordProtection) && !(protectionParameter instanceof CallbackHandlerProtection)) {
                throw new IllegalArgumentException("Protection must be PasswordProtection or CallbackHandlerProtection");
            }
            if (!file.isFile()) {
                throw new IllegalArgumentException("File does not exist or it does not refer to a normal file: " + file);
            }
            return new FileBuilder(s, provider, file, protectionParameter, AccessController.getContext());
        }
        
        public static Builder newInstance(final String s, final Provider provider, final ProtectionParameter protectionParameter) {
            if (s == null || protectionParameter == null) {
                throw new NullPointerException();
            }
            return new Builder() {
                private volatile boolean getCalled;
                private IOException oldException;
                private final PrivilegedExceptionAction<KeyStore> action = new PrivilegedExceptionAction<KeyStore>() {
                    @Override
                    public KeyStore run() throws Exception {
                        KeyStore keyStore;
                        if (provider == null) {
                            keyStore = KeyStore.getInstance(s);
                        }
                        else {
                            keyStore = KeyStore.getInstance(s, provider);
                        }
                        final SimpleLoadStoreParameter simpleLoadStoreParameter = new SimpleLoadStoreParameter(protectionParameter);
                        if (!(protectionParameter instanceof CallbackHandlerProtection)) {
                            keyStore.load(simpleLoadStoreParameter);
                        }
                        else {
                            int n = 0;
                            while (true) {
                                ++n;
                                try {
                                    keyStore.load(simpleLoadStoreParameter);
                                }
                                catch (IOException ex) {
                                    if (ex.getCause() instanceof UnrecoverableKeyException) {
                                        if (n < 3) {
                                            continue;
                                        }
                                        ((KeyStore$Builder$2)provider).oldException = ex;
                                    }
                                    throw ex;
                                }
                                break;
                            }
                        }
                        ((KeyStore$Builder$2)provider).getCalled = true;
                        return keyStore;
                    }
                };
                final /* synthetic */ AccessControlContext val$context = AccessController.getContext();
                
                @Override
                public synchronized KeyStore getKeyStore() throws KeyStoreException {
                    if (this.oldException != null) {
                        throw new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
                    }
                    try {
                        return AccessController.doPrivileged(this.action, this.val$context);
                    }
                    catch (PrivilegedActionException ex) {
                        throw new KeyStoreException("KeyStore instantiation failed", ex.getCause());
                    }
                }
                
                @Override
                public ProtectionParameter getProtectionParameter(final String s) {
                    if (s == null) {
                        throw new NullPointerException();
                    }
                    if (!this.getCalled) {
                        throw new IllegalStateException("getKeyStore() must be called first");
                    }
                    return protectionParameter;
                }
            };
        }
        
        private static final class FileBuilder extends Builder
        {
            private final String type;
            private final Provider provider;
            private final File file;
            private ProtectionParameter protection;
            private ProtectionParameter keyProtection;
            private final AccessControlContext context;
            private KeyStore keyStore;
            private Throwable oldException;
            
            FileBuilder(final String type, final Provider provider, final File file, final ProtectionParameter protection, final AccessControlContext context) {
                this.type = type;
                this.provider = provider;
                this.file = file;
                this.protection = protection;
                this.context = context;
            }
            
            @Override
            public synchronized KeyStore getKeyStore() throws KeyStoreException {
                if (this.keyStore != null) {
                    return this.keyStore;
                }
                if (this.oldException != null) {
                    throw new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
                }
                final PrivilegedExceptionAction<KeyStore> privilegedExceptionAction = new PrivilegedExceptionAction<KeyStore>() {
                    @Override
                    public KeyStore run() throws Exception {
                        if (!(FileBuilder.this.protection instanceof CallbackHandlerProtection)) {
                            return this.run0();
                        }
                        int n = 0;
                        while (true) {
                            ++n;
                            try {
                                return this.run0();
                            }
                            catch (IOException ex) {
                                if (n < 3 && ex.getCause() instanceof UnrecoverableKeyException) {
                                    continue;
                                }
                                throw ex;
                            }
                        }
                    }
                    
                    public KeyStore run0() throws Exception {
                        KeyStore keyStore;
                        if (FileBuilder.this.provider == null) {
                            keyStore = KeyStore.getInstance(FileBuilder.this.type);
                        }
                        else {
                            keyStore = KeyStore.getInstance(FileBuilder.this.type, FileBuilder.this.provider);
                        }
                        InputStream inputStream = null;
                        try {
                            inputStream = new FileInputStream(FileBuilder.this.file);
                            char[] array;
                            if (FileBuilder.this.protection instanceof PasswordProtection) {
                                array = ((PasswordProtection)FileBuilder.this.protection).getPassword();
                                FileBuilder.this.keyProtection = FileBuilder.this.protection;
                            }
                            else {
                                final CallbackHandler callbackHandler = ((CallbackHandlerProtection)FileBuilder.this.protection).getCallbackHandler();
                                final PasswordCallback passwordCallback = new PasswordCallback("Password for keystore " + FileBuilder.this.file.getName(), false);
                                callbackHandler.handle(new Callback[] { passwordCallback });
                                array = passwordCallback.getPassword();
                                if (array == null) {
                                    throw new KeyStoreException("No password provided");
                                }
                                passwordCallback.clearPassword();
                                FileBuilder.this.keyProtection = new PasswordProtection(array);
                            }
                            keyStore.load(inputStream, array);
                            return keyStore;
                        }
                        finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        }
                    }
                };
                try {
                    return this.keyStore = AccessController.doPrivileged((PrivilegedExceptionAction<KeyStore>)privilegedExceptionAction, this.context);
                }
                catch (PrivilegedActionException ex) {
                    this.oldException = ex.getCause();
                    throw new KeyStoreException("KeyStore instantiation failed", this.oldException);
                }
            }
            
            @Override
            public synchronized ProtectionParameter getProtectionParameter(final String s) {
                if (s == null) {
                    throw new NullPointerException();
                }
                if (this.keyStore == null) {
                    throw new IllegalStateException("getKeyStore() must be called first");
                }
                return this.keyProtection;
            }
        }
    }
    
    public static class CallbackHandlerProtection implements ProtectionParameter
    {
        private final CallbackHandler handler;
        
        public CallbackHandlerProtection(final CallbackHandler handler) {
            if (handler == null) {
                throw new NullPointerException("handler must not be null");
            }
            this.handler = handler;
        }
        
        public CallbackHandler getCallbackHandler() {
            return this.handler;
        }
    }
    
    public static class PasswordProtection implements ProtectionParameter, Destroyable
    {
        private final char[] password;
        private final String protectionAlgorithm;
        private final AlgorithmParameterSpec protectionParameters;
        private volatile boolean destroyed;
        
        public PasswordProtection(final char[] array) {
            this.destroyed = false;
            this.password = (char[])((array == null) ? null : ((char[])array.clone()));
            this.protectionAlgorithm = null;
            this.protectionParameters = null;
        }
        
        public PasswordProtection(final char[] array, final String protectionAlgorithm, final AlgorithmParameterSpec protectionParameters) {
            this.destroyed = false;
            if (protectionAlgorithm == null) {
                throw new NullPointerException("invalid null input");
            }
            this.password = (char[])((array == null) ? null : ((char[])array.clone()));
            this.protectionAlgorithm = protectionAlgorithm;
            this.protectionParameters = protectionParameters;
        }
        
        public String getProtectionAlgorithm() {
            return this.protectionAlgorithm;
        }
        
        public AlgorithmParameterSpec getProtectionParameters() {
            return this.protectionParameters;
        }
        
        public synchronized char[] getPassword() {
            if (this.destroyed) {
                throw new IllegalStateException("password has been cleared");
            }
            return this.password;
        }
        
        @Override
        public synchronized void destroy() throws DestroyFailedException {
            this.destroyed = true;
            if (this.password != null) {
                Arrays.fill(this.password, ' ');
            }
        }
        
        @Override
        public synchronized boolean isDestroyed() {
            return this.destroyed;
        }
    }
    
    public interface LoadStoreParameter
    {
        ProtectionParameter getProtectionParameter();
    }
    
    static class SimpleLoadStoreParameter implements LoadStoreParameter
    {
        private final ProtectionParameter protection;
        
        SimpleLoadStoreParameter(final ProtectionParameter protection) {
            this.protection = protection;
        }
        
        @Override
        public ProtectionParameter getProtectionParameter() {
            return this.protection;
        }
    }
    
    public interface Entry
    {
        default Set<Attribute> getAttributes() {
            return Collections.emptySet();
        }
        
        public interface Attribute
        {
            String getName();
            
            String getValue();
        }
    }
    
    public static final class PrivateKeyEntry implements Entry
    {
        private final PrivateKey privKey;
        private final Certificate[] chain;
        private final Set<Attribute> attributes;
        
        public PrivateKeyEntry(final PrivateKey privateKey, final Certificate[] array) {
            this(privateKey, array, Collections.emptySet());
        }
        
        public PrivateKeyEntry(final PrivateKey privKey, final Certificate[] array, final Set<Attribute> set) {
            if (privKey == null || array == null || set == null) {
                throw new NullPointerException("invalid null input");
            }
            if (array.length == 0) {
                throw new IllegalArgumentException("invalid zero-length input chain");
            }
            final Certificate[] chain = array.clone();
            final String type = chain[0].getType();
            for (int i = 1; i < chain.length; ++i) {
                if (!type.equals(chain[i].getType())) {
                    throw new IllegalArgumentException("chain does not contain certificates of the same type");
                }
            }
            if (!privKey.getAlgorithm().equals(chain[0].getPublicKey().getAlgorithm())) {
                throw new IllegalArgumentException("private key algorithm does not match algorithm of public key in end entity certificate (at index 0)");
            }
            this.privKey = privKey;
            if (chain[0] instanceof X509Certificate && !(chain instanceof X509Certificate[])) {
                System.arraycopy(chain, 0, this.chain = new X509Certificate[chain.length], 0, chain.length);
            }
            else {
                this.chain = chain;
            }
            this.attributes = Collections.unmodifiableSet((Set<? extends Attribute>)new HashSet<Attribute>(set));
        }
        
        public PrivateKey getPrivateKey() {
            return this.privKey;
        }
        
        public Certificate[] getCertificateChain() {
            return this.chain.clone();
        }
        
        public Certificate getCertificate() {
            return this.chain[0];
        }
        
        @Override
        public Set<Attribute> getAttributes() {
            return this.attributes;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("Private key entry and certificate chain with " + this.chain.length + " elements:\r\n");
            final Certificate[] chain = this.chain;
            for (int length = chain.length, i = 0; i < length; ++i) {
                sb.append(chain[i]);
                sb.append("\r\n");
            }
            return sb.toString();
        }
    }
    
    public interface ProtectionParameter
    {
    }
    
    public static final class SecretKeyEntry implements Entry
    {
        private final SecretKey sKey;
        private final Set<Attribute> attributes;
        
        public SecretKeyEntry(final SecretKey sKey) {
            if (sKey == null) {
                throw new NullPointerException("invalid null input");
            }
            this.sKey = sKey;
            this.attributes = Collections.emptySet();
        }
        
        public SecretKeyEntry(final SecretKey sKey, final Set<Attribute> set) {
            if (sKey == null || set == null) {
                throw new NullPointerException("invalid null input");
            }
            this.sKey = sKey;
            this.attributes = Collections.unmodifiableSet((Set<? extends Attribute>)new HashSet<Attribute>(set));
        }
        
        public SecretKey getSecretKey() {
            return this.sKey;
        }
        
        @Override
        public Set<Attribute> getAttributes() {
            return this.attributes;
        }
        
        @Override
        public String toString() {
            return "Secret key entry with algorithm " + this.sKey.getAlgorithm();
        }
    }
    
    public static final class TrustedCertificateEntry implements Entry
    {
        private final Certificate cert;
        private final Set<Attribute> attributes;
        
        public TrustedCertificateEntry(final Certificate cert) {
            if (cert == null) {
                throw new NullPointerException("invalid null input");
            }
            this.cert = cert;
            this.attributes = Collections.emptySet();
        }
        
        public TrustedCertificateEntry(final Certificate cert, final Set<Attribute> set) {
            if (cert == null || set == null) {
                throw new NullPointerException("invalid null input");
            }
            this.cert = cert;
            this.attributes = Collections.unmodifiableSet((Set<? extends Attribute>)new HashSet<Attribute>(set));
        }
        
        public Certificate getTrustedCertificate() {
            return this.cert;
        }
        
        @Override
        public Set<Attribute> getAttributes() {
            return this.attributes;
        }
        
        @Override
        public String toString() {
            return "Trusted certificate entry:\r\n" + this.cert.toString();
        }
    }
}
