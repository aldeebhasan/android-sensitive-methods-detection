package android.net.sip;

import android.content.*;
import android.os.*;

public class SipAudioCall
{
    public SipAudioCall(final Context context, final SipProfile localProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void setListener(final Listener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setListener(final Listener listener, final boolean callbackImmediately) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInCall() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOnHold() {
        throw new RuntimeException("Stub!");
    }
    
    public void close() {
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
    
    public void attachCall(final SipSession session, final String sessionDescription) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void makeCall(final SipProfile peerProfile, final SipSession sipSession, final int timeout) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void endCall() throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void holdCall(final int timeout) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void answerCall(final int timeout) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void continueCall(final int timeout) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void toggleMute() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isMuted() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSpeakerMode(final boolean speakerMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendDtmf(final int code) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendDtmf(final int code, final Message result) {
        throw new RuntimeException("Stub!");
    }
    
    public void startAudio() {
        throw new RuntimeException("Stub!");
    }
    
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
}
