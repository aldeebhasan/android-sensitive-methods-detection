package android.net.sip;

public static class Listener
{
    public Listener() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCalling(final SipSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRinging(final SipSession session, final SipProfile caller, final String sessionDescription) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRingingBack(final SipSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallEstablished(final SipSession session, final String sessionDescription) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallEnded(final SipSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallBusy(final SipSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onError(final SipSession session, final int errorCode, final String errorMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallChangeFailed(final SipSession session, final int errorCode, final String errorMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRegistering(final SipSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRegistrationDone(final SipSession session, final int duration) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRegistrationFailed(final SipSession session, final int errorCode, final String errorMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRegistrationTimeout(final SipSession session) {
        throw new RuntimeException("Stub!");
    }
}
