package android.net.sip;

import android.app.*;
import android.content.*;

public class SipManager
{
    public static final String EXTRA_CALL_ID = "android:sipCallID";
    public static final String EXTRA_OFFER_SD = "android:sipOfferSD";
    public static final int INCOMING_CALL_RESULT_CODE = 101;
    
    SipManager() {
        throw new RuntimeException("Stub!");
    }
    
    public static SipManager newInstance(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isApiSupported(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isVoipSupported(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isSipWifiOnly(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void open(final SipProfile localProfile) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void open(final SipProfile localProfile, final PendingIntent incomingCallPendingIntent, final SipRegistrationListener listener) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void setRegistrationListener(final String localProfileUri, final SipRegistrationListener listener) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void close(final String localProfileUri) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOpened(final String localProfileUri) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRegistered(final String localProfileUri) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public SipAudioCall makeAudioCall(final SipProfile localProfile, final SipProfile peerProfile, final SipAudioCall.Listener listener, final int timeout) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public SipAudioCall makeAudioCall(final String localProfileUri, final String peerProfileUri, final SipAudioCall.Listener listener, final int timeout) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public SipAudioCall takeAudioCall(final Intent incomingCallIntent, final SipAudioCall.Listener listener) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isIncomingCallIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getCallId(final Intent incomingCallIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getOfferSessionDescription(final Intent incomingCallIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public void register(final SipProfile localProfile, final int expiryTime, final SipRegistrationListener listener) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public void unregister(final SipProfile localProfile, final SipRegistrationListener listener) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public SipSession getSessionFor(final Intent incomingCallIntent) throws SipException {
        throw new RuntimeException("Stub!");
    }
    
    public SipSession createSipSession(final SipProfile localProfile, final SipSession.Listener listener) throws SipException {
        throw new RuntimeException("Stub!");
    }
}
