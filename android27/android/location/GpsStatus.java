package android.location;

@Deprecated
public final class GpsStatus
{
    public static final int GPS_EVENT_FIRST_FIX = 3;
    public static final int GPS_EVENT_SATELLITE_STATUS = 4;
    public static final int GPS_EVENT_STARTED = 1;
    public static final int GPS_EVENT_STOPPED = 2;
    
    GpsStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTimeToFirstFix() {
        throw new RuntimeException("Stub!");
    }
    
    public Iterable<GpsSatellite> getSatellites() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxSatellites() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public interface NmeaListener
    {
        void onNmeaReceived(final long p0, final String p1);
    }
    
    @Deprecated
    public interface Listener
    {
        void onGpsStatusChanged(final int p0);
    }
}
