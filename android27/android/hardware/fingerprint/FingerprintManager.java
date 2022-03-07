package android.hardware.fingerprint;

import android.os.*;
import java.security.*;
import javax.crypto.*;

public class FingerprintManager
{
    public static final int FINGERPRINT_ACQUIRED_GOOD = 0;
    public static final int FINGERPRINT_ACQUIRED_IMAGER_DIRTY = 3;
    public static final int FINGERPRINT_ACQUIRED_INSUFFICIENT = 2;
    public static final int FINGERPRINT_ACQUIRED_PARTIAL = 1;
    public static final int FINGERPRINT_ACQUIRED_TOO_FAST = 5;
    public static final int FINGERPRINT_ACQUIRED_TOO_SLOW = 4;
    public static final int FINGERPRINT_ERROR_CANCELED = 5;
    public static final int FINGERPRINT_ERROR_HW_UNAVAILABLE = 1;
    public static final int FINGERPRINT_ERROR_LOCKOUT = 7;
    public static final int FINGERPRINT_ERROR_LOCKOUT_PERMANENT = 9;
    public static final int FINGERPRINT_ERROR_NO_SPACE = 4;
    public static final int FINGERPRINT_ERROR_TIMEOUT = 3;
    public static final int FINGERPRINT_ERROR_UNABLE_TO_PROCESS = 2;
    public static final int FINGERPRINT_ERROR_USER_CANCELED = 10;
    public static final int FINGERPRINT_ERROR_VENDOR = 8;
    
    FingerprintManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void authenticate(final CryptoObject crypto, final CancellationSignal cancel, final int flags, final AuthenticationCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasEnrolledFingerprints() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isHardwareDetected() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class CryptoObject
    {
        public CryptoObject(final Signature signature) {
            throw new RuntimeException("Stub!");
        }
        
        public CryptoObject(final Cipher cipher) {
            throw new RuntimeException("Stub!");
        }
        
        public CryptoObject(final Mac mac) {
            throw new RuntimeException("Stub!");
        }
        
        public Signature getSignature() {
            throw new RuntimeException("Stub!");
        }
        
        public Cipher getCipher() {
            throw new RuntimeException("Stub!");
        }
        
        public Mac getMac() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class AuthenticationResult
    {
        AuthenticationResult() {
            throw new RuntimeException("Stub!");
        }
        
        public CryptoObject getCryptoObject() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public abstract static class AuthenticationCallback
    {
        public AuthenticationCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onAuthenticationError(final int errorCode, final CharSequence errString) {
            throw new RuntimeException("Stub!");
        }
        
        public void onAuthenticationHelp(final int helpCode, final CharSequence helpString) {
            throw new RuntimeException("Stub!");
        }
        
        public void onAuthenticationSucceeded(final AuthenticationResult result) {
            throw new RuntimeException("Stub!");
        }
        
        public void onAuthenticationFailed() {
            throw new RuntimeException("Stub!");
        }
    }
}
