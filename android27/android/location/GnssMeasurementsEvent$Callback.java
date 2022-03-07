package android.location;

public abstract static class Callback
{
    public static final int STATUS_LOCATION_DISABLED = 2;
    public static final int STATUS_NOT_SUPPORTED = 0;
    public static final int STATUS_READY = 1;
    
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onGnssMeasurementsReceived(final GnssMeasurementsEvent eventArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStatusChanged(final int status) {
        throw new RuntimeException("Stub!");
    }
}
