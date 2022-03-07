package java.security;

import java.util.*;
import java.security.cert.*;
import java.io.*;
import javax.security.auth.callback.*;
import javax.crypto.*;

public abstract class KeyStoreSpi
{
    public abstract Key engineGetKey(final String p0, final char[] p1) throws NoSuchAlgorithmException, UnrecoverableKeyException;
    
    public abstract Certificate[] engineGetCertificateChain(final String p0);
    
    public abstract Certificate engineGetCertificate(final String p0);
    
    public abstract Date engineGetCreationDate(final String p0);
    
    public abstract void engineSetKeyEntry(final String p0, final Key p1, final char[] p2, final Certificate[] p3) throws KeyStoreException;
    
    public abstract void engineSetKeyEntry(final String p0, final byte[] p1, final Certificate[] p2) throws KeyStoreException;
    
    public abstract void engineSetCertificateEntry(final String p0, final Certificate p1) throws KeyStoreException;
    
    public abstract void engineDeleteEntry(final String p0) throws KeyStoreException;
    
    public abstract Enumeration<String> engineAliases();
    
    public abstract boolean engineContainsAlias(final String p0);
    
    public abstract int engineSize();
    
    public abstract boolean engineIsKeyEntry(final String p0);
    
    public abstract boolean engineIsCertificateEntry(final String p0);
    
    public abstract String engineGetCertificateAlias(final Certificate p0);
    
    public abstract void engineStore(final OutputStream p0, final char[] p1) throws IOException, NoSuchAlgorithmException, CertificateException;
    
    public void engineStore(final KeyStore.LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        throw new UnsupportedOperationException();
    }
    
    public abstract void engineLoad(final InputStream p0, final char[] p1) throws IOException, NoSuchAlgorithmException, CertificateException;
    
    public void engineLoad(final KeyStore.LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        if (loadStoreParameter == null) {
            this.engineLoad(null, null);
            return;
        }
        if (loadStoreParameter instanceof KeyStore.SimpleLoadStoreParameter) {
            final KeyStore.ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
            char[] array;
            if (protectionParameter instanceof KeyStore.PasswordProtection) {
                array = ((KeyStore.PasswordProtection)protectionParameter).getPassword();
            }
            else {
                if (!(protectionParameter instanceof KeyStore.CallbackHandlerProtection)) {
                    throw new NoSuchAlgorithmException("ProtectionParameter must be PasswordProtection or CallbackHandlerProtection");
                }
                final CallbackHandler callbackHandler = ((KeyStore.CallbackHandlerProtection)protectionParameter).getCallbackHandler();
                final PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
                try {
                    callbackHandler.handle(new Callback[] { passwordCallback });
                }
                catch (UnsupportedCallbackException ex) {
                    throw new NoSuchAlgorithmException("Could not obtain password", ex);
                }
                array = passwordCallback.getPassword();
                passwordCallback.clearPassword();
                if (array == null) {
                    throw new NoSuchAlgorithmException("No password provided");
                }
            }
            this.engineLoad(null, array);
            return;
        }
        throw new UnsupportedOperationException();
    }
    
    public KeyStore.Entry engineGetEntry(final String s, final KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
        if (!this.engineContainsAlias(s)) {
            return null;
        }
        if (protectionParameter != null) {
            if (protectionParameter instanceof KeyStore.PasswordProtection) {
                if (this.engineIsCertificateEntry(s)) {
                    throw new UnsupportedOperationException("trusted certificate entries are not password-protected");
                }
                if (this.engineIsKeyEntry(s)) {
                    final Key engineGetKey = this.engineGetKey(s, ((KeyStore.PasswordProtection)protectionParameter).getPassword());
                    if (engineGetKey instanceof PrivateKey) {
                        return new KeyStore.PrivateKeyEntry((PrivateKey)engineGetKey, this.engineGetCertificateChain(s));
                    }
                    if (engineGetKey instanceof SecretKey) {
                        return new KeyStore.SecretKeyEntry((SecretKey)engineGetKey);
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
        if (this.engineIsCertificateEntry(s)) {
            return new KeyStore.TrustedCertificateEntry(this.engineGetCertificate(s));
        }
        throw new UnrecoverableKeyException("requested entry requires a password");
    }
    
    public void engineSetEntry(final String s, final KeyStore.Entry entry, final KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException {
        if (protectionParameter != null && !(protectionParameter instanceof KeyStore.PasswordProtection)) {
            throw new KeyStoreException("unsupported protection parameter");
        }
        KeyStore.PasswordProtection passwordProtection = null;
        if (protectionParameter != null) {
            passwordProtection = (KeyStore.PasswordProtection)protectionParameter;
        }
        if (entry instanceof KeyStore.TrustedCertificateEntry) {
            if (protectionParameter != null && passwordProtection.getPassword() != null) {
                throw new KeyStoreException("trusted certificate entries are not password-protected");
            }
            this.engineSetCertificateEntry(s, ((KeyStore.TrustedCertificateEntry)entry).getTrustedCertificate());
        }
        else if (entry instanceof KeyStore.PrivateKeyEntry) {
            if (passwordProtection == null || passwordProtection.getPassword() == null) {
                throw new KeyStoreException("non-null password required to create PrivateKeyEntry");
            }
            this.engineSetKeyEntry(s, ((KeyStore.PrivateKeyEntry)entry).getPrivateKey(), passwordProtection.getPassword(), ((KeyStore.PrivateKeyEntry)entry).getCertificateChain());
        }
        else {
            if (!(entry instanceof KeyStore.SecretKeyEntry)) {
                throw new KeyStoreException("unsupported entry type: " + entry.getClass().getName());
            }
            if (passwordProtection == null || passwordProtection.getPassword() == null) {
                throw new KeyStoreException("non-null password required to create SecretKeyEntry");
            }
            this.engineSetKeyEntry(s, ((KeyStore.SecretKeyEntry)entry).getSecretKey(), passwordProtection.getPassword(), null);
        }
    }
    
    public boolean engineEntryInstanceOf(final String s, final Class<? extends KeyStore.Entry> clazz) {
        if (clazz == KeyStore.TrustedCertificateEntry.class) {
            return this.engineIsCertificateEntry(s);
        }
        if (clazz == KeyStore.PrivateKeyEntry.class) {
            return this.engineIsKeyEntry(s) && this.engineGetCertificate(s) != null;
        }
        return clazz == KeyStore.SecretKeyEntry.class && this.engineIsKeyEntry(s) && this.engineGetCertificate(s) == null;
    }
}
