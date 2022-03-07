package android.security;

import android.app.*;
import android.net.*;
import android.content.*;
import java.security.*;
import java.security.cert.*;

public final class KeyChain
{
    public static final String ACTION_KEYCHAIN_CHANGED = "android.security.action.KEYCHAIN_CHANGED";
    public static final String ACTION_KEY_ACCESS_CHANGED = "android.security.action.KEY_ACCESS_CHANGED";
    @Deprecated
    public static final String ACTION_STORAGE_CHANGED = "android.security.STORAGE_CHANGED";
    public static final String ACTION_TRUST_STORE_CHANGED = "android.security.action.TRUST_STORE_CHANGED";
    public static final String EXTRA_CERTIFICATE = "CERT";
    public static final String EXTRA_KEY_ACCESSIBLE = "android.security.extra.KEY_ACCESSIBLE";
    public static final String EXTRA_KEY_ALIAS = "android.security.extra.KEY_ALIAS";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_PKCS12 = "PKCS12";
    
    public KeyChain() {
        throw new RuntimeException("Stub!");
    }
    
    public static Intent createInstallIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public static void choosePrivateKeyAlias(final Activity activity, final KeyChainAliasCallback response, final String[] keyTypes, final Principal[] issuers, final String host, final int port, final String alias) {
        throw new RuntimeException("Stub!");
    }
    
    public static void choosePrivateKeyAlias(final Activity activity, final KeyChainAliasCallback response, final String[] keyTypes, final Principal[] issuers, final Uri uri, final String alias) {
        throw new RuntimeException("Stub!");
    }
    
    public static PrivateKey getPrivateKey(final Context context, final String alias) throws KeyChainException, InterruptedException {
        throw new RuntimeException("Stub!");
    }
    
    public static X509Certificate[] getCertificateChain(final Context context, final String alias) throws KeyChainException, InterruptedException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isKeyAlgorithmSupported(final String algorithm) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static boolean isBoundKeyAlgorithm(final String algorithm) {
        throw new RuntimeException("Stub!");
    }
}
