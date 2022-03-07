package android.telephony;

import java.util.*;

public class PhoneStateListener
{
    public static final int LISTEN_CALL_FORWARDING_INDICATOR = 8;
    public static final int LISTEN_CALL_STATE = 32;
    public static final int LISTEN_CELL_INFO = 1024;
    public static final int LISTEN_CELL_LOCATION = 16;
    public static final int LISTEN_DATA_ACTIVITY = 128;
    public static final int LISTEN_DATA_CONNECTION_STATE = 64;
    public static final int LISTEN_MESSAGE_WAITING_INDICATOR = 4;
    public static final int LISTEN_NONE = 0;
    public static final int LISTEN_SERVICE_STATE = 1;
    @Deprecated
    public static final int LISTEN_SIGNAL_STRENGTH = 2;
    public static final int LISTEN_SIGNAL_STRENGTHS = 256;
    
    public PhoneStateListener() {
        throw new RuntimeException("Stub!");
    }
    
    public void onServiceStateChanged(final ServiceState serviceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onSignalStrengthChanged(final int asu) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMessageWaitingIndicatorChanged(final boolean mwi) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallForwardingIndicatorChanged(final boolean cfi) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCellLocationChanged(final CellLocation location) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallStateChanged(final int state, final String incomingNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDataConnectionStateChanged(final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDataConnectionStateChanged(final int state, final int networkType) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDataActivity(final int direction) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSignalStrengthsChanged(final SignalStrength signalStrength) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCellInfoChanged(final List<CellInfo> cellInfo) {
        throw new RuntimeException("Stub!");
    }
}
