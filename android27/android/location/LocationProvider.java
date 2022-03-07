package android.location;

public class LocationProvider
{
    public static final int AVAILABLE = 2;
    public static final int OUT_OF_SERVICE = 0;
    public static final int TEMPORARILY_UNAVAILABLE = 1;
    
    LocationProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean meetsCriteria(final Criteria criteria) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean requiresNetwork() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean requiresSatellite() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean requiresCell() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasMonetaryCost() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean supportsAltitude() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean supportsSpeed() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean supportsBearing() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPowerRequirement() {
        throw new RuntimeException("Stub!");
    }
    
    public int getAccuracy() {
        throw new RuntimeException("Stub!");
    }
}
