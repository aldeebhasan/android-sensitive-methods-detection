package android.net.wifi;

public static class LocalOnlyHotspotCallback
{
    public static final int ERROR_GENERIC = 2;
    public static final int ERROR_INCOMPATIBLE_MODE = 3;
    public static final int ERROR_NO_CHANNEL = 1;
    public static final int ERROR_TETHERING_DISALLOWED = 4;
    
    public LocalOnlyHotspotCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStarted(final LocalOnlyHotspotReservation reservation) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStopped() {
        throw new RuntimeException("Stub!");
    }
    
    public void onFailed(final int reason) {
        throw new RuntimeException("Stub!");
    }
}
