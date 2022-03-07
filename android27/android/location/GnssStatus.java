package android.location;

public final class GnssStatus
{
    public static final int CONSTELLATION_BEIDOU = 5;
    public static final int CONSTELLATION_GALILEO = 6;
    public static final int CONSTELLATION_GLONASS = 3;
    public static final int CONSTELLATION_GPS = 1;
    public static final int CONSTELLATION_QZSS = 4;
    public static final int CONSTELLATION_SBAS = 2;
    public static final int CONSTELLATION_UNKNOWN = 0;
    
    GnssStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSatelliteCount() {
        throw new RuntimeException("Stub!");
    }
    
    public int getConstellationType(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSvid(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public float getCn0DbHz(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public float getElevationDegrees(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public float getAzimuthDegrees(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasEphemerisData(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasAlmanacData(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean usedInFix(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasCarrierFrequencyHz(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public float getCarrierFrequencyHz(final int satIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onStarted() {
            throw new RuntimeException("Stub!");
        }
        
        public void onStopped() {
            throw new RuntimeException("Stub!");
        }
        
        public void onFirstFix(final int ttffMillis) {
            throw new RuntimeException("Stub!");
        }
        
        public void onSatelliteStatusChanged(final GnssStatus status) {
            throw new RuntimeException("Stub!");
        }
    }
}
