package android.provider;

import android.net.*;

public static final class ServiceStateTable
{
    public static final String AUTHORITY = "service-state";
    public static final Uri CONTENT_URI;
    public static final String IS_MANUAL_NETWORK_SELECTION = "is_manual_network_selection";
    public static final String VOICE_OPERATOR_NUMERIC = "voice_operator_numeric";
    public static final String VOICE_REG_STATE = "voice_reg_state";
    
    ServiceStateTable() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getUriForSubscriptionIdAndField(final int subscriptionId, final String field) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getUriForSubscriptionId(final int subscriptionId) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
