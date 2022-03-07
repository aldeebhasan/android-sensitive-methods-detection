package android.net.sip;

public final class SipSession
{
    SipSession() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLocalIp() {
        throw new RuntimeException("Stub!");
    }
    
    public SipProfile getLocalProfile() {
        throw new RuntimeException("Stub!");
    }
    
    public SipProfile getPeerProfile() {
        throw new RuntimeException("Stub!");
    }
    
    public int getState() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInCall() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCallId() {
        throw new RuntimeException("Stub!");
    }
    
    public void setListener(final Listener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void register(final int duration) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregister() {
        throw new RuntimeException("Stub!");
    }
    
    public void makeCall(final SipProfile callee, final String sessionDescription, final int timeout) {
        throw new RuntimeException("Stub!");
    }
    
    public void answerCall(final String sessionDescription, final int timeout) {
        throw new RuntimeException("Stub!");
    }
    
    public void endCall() {
        throw new RuntimeException("Stub!");
    }
    
    public void changeCall(final String sessionDescription, final int timeout) {
        throw new RuntimeException("Stub!");
    }
    
    public static class State
    {
        public static final int DEREGISTERING = 2;
        public static final int INCOMING_CALL = 3;
        public static final int INCOMING_CALL_ANSWERING = 4;
        public static final int IN_CALL = 8;
        public static final int NOT_DEFINED = 101;
        public static final int OUTGOING_CALL = 5;
        public static final int OUTGOING_CALL_CANCELING = 7;
        public static final int OUTGOING_CALL_RING_BACK = 6;
        public static final int PINGING = 9;
        public static final int READY_TO_CALL = 0;
        public static final int REGISTERING = 1;
        
        State() {
            throw new RuntimeException("Stub!");
        }
        
        public static String toString(final int state) {
            throw new RuntimeException("Stub!");
        }
    }
    
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
}
