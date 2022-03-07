package android.telecom;

import android.net.*;
import android.os.*;

public static class Details
{
    public static final int CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO = 4194304;
    public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
    public static final int CAPABILITY_CAN_PULL_CALL = 8388608;
    public static final int CAPABILITY_DISCONNECT_FROM_CONFERENCE = 8192;
    public static final int CAPABILITY_HOLD = 1;
    public static final int CAPABILITY_MANAGE_CONFERENCE = 128;
    public static final int CAPABILITY_MERGE_CONFERENCE = 4;
    public static final int CAPABILITY_MUTE = 64;
    public static final int CAPABILITY_RESPOND_VIA_TEXT = 32;
    public static final int CAPABILITY_SEPARATE_FROM_CONFERENCE = 4096;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 768;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_RX = 256;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_TX = 512;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 3072;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_RX = 1024;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_TX = 2048;
    public static final int CAPABILITY_SUPPORT_HOLD = 2;
    public static final int CAPABILITY_SWAP_CONFERENCE = 8;
    public static final int PROPERTY_CONFERENCE = 1;
    public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 4;
    public static final int PROPERTY_ENTERPRISE_CALL = 32;
    public static final int PROPERTY_GENERIC_CONFERENCE = 2;
    public static final int PROPERTY_HAS_CDMA_VOICE_PRIVACY = 128;
    public static final int PROPERTY_HIGH_DEF_AUDIO = 16;
    public static final int PROPERTY_IS_EXTERNAL_CALL = 64;
    public static final int PROPERTY_SELF_MANAGED = 256;
    public static final int PROPERTY_WIFI = 8;
    
    Details() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean can(final int capabilities, final int capability) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean can(final int capability) {
        throw new RuntimeException("Stub!");
    }
    
    public static String capabilitiesToString(final int capabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean hasProperty(final int properties, final int property) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasProperty(final int property) {
        throw new RuntimeException("Stub!");
    }
    
    public static String propertiesToString(final int properties) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHandlePresentation() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCallerDisplayName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCallerDisplayNamePresentation() {
        throw new RuntimeException("Stub!");
    }
    
    public PhoneAccountHandle getAccountHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCallCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCallProperties() {
        throw new RuntimeException("Stub!");
    }
    
    public DisconnectCause getDisconnectCause() {
        throw new RuntimeException("Stub!");
    }
    
    public final long getConnectTimeMillis() {
        throw new RuntimeException("Stub!");
    }
    
    public GatewayInfo getGatewayInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getVideoState() {
        throw new RuntimeException("Stub!");
    }
    
    public StatusHints getStatusHints() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getIntentExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public long getCreationTimeMillis() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
