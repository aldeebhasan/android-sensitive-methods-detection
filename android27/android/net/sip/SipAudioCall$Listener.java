package android.net.sip;

public static class Listener
{
    public Listener() {
        throw new RuntimeException("Stub!");
    }
    
    public void onReadyToCall(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCalling(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRinging(final SipAudioCall call, final SipProfile caller) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRingingBack(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallEstablished(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallEnded(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallBusy(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallHeld(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onError(final SipAudioCall call, final int errorCode, final String errorMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public void onChanged(final SipAudioCall call) {
        throw new RuntimeException("Stub!");
    }
}
