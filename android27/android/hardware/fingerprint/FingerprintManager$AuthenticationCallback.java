package android.hardware.fingerprint;

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
