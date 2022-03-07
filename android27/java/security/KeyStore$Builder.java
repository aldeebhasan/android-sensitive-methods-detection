package java.security;

import java.io.*;
import javax.security.auth.callback.*;

public abstract static class Builder
{
    static final int MAX_CALLBACK_TRIES = 3;
    
    public abstract KeyStore getKeyStore() throws KeyStoreException;
    
    public abstract ProtectionParameter getProtectionParameter(final String p0) throws KeyStoreException;
    
    public static Builder newInstance(final KeyStore keyStore, final ProtectionParameter protectionParameter) {
        if (keyStore == null || protectionParameter == null) {
            throw new NullPointerException();
        }
        if (!KeyStore.access$000(keyStore)) {
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
