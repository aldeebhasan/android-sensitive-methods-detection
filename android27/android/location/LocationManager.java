package android.location;

import java.util.*;
import android.app.*;
import android.os.*;

public class LocationManager
{
    public static final String GPS_PROVIDER = "gps";
    public static final String KEY_LOCATION_CHANGED = "location";
    public static final String KEY_PROVIDER_ENABLED = "providerEnabled";
    public static final String KEY_PROXIMITY_ENTERING = "entering";
    public static final String KEY_STATUS_CHANGED = "status";
    public static final String MODE_CHANGED_ACTION = "android.location.MODE_CHANGED";
    public static final String NETWORK_PROVIDER = "network";
    public static final String PASSIVE_PROVIDER = "passive";
    public static final String PROVIDERS_CHANGED_ACTION = "android.location.PROVIDERS_CHANGED";
    
    LocationManager() {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getAllProviders() {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getProviders(final boolean enabledOnly) {
        throw new RuntimeException("Stub!");
    }
    
    public LocationProvider getProvider(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getProviders(final Criteria criteria, final boolean enabledOnly) {
        throw new RuntimeException("Stub!");
    }
    
    public String getBestProvider(final Criteria criteria, final boolean enabledOnly) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestLocationUpdates(final String provider, final long minTime, final float minDistance, final LocationListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestLocationUpdates(final String provider, final long minTime, final float minDistance, final LocationListener listener, final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestLocationUpdates(final long minTime, final float minDistance, final Criteria criteria, final LocationListener listener, final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestLocationUpdates(final String provider, final long minTime, final float minDistance, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestLocationUpdates(final long minTime, final float minDistance, final Criteria criteria, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestSingleUpdate(final String provider, final LocationListener listener, final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestSingleUpdate(final Criteria criteria, final LocationListener listener, final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestSingleUpdate(final String provider, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestSingleUpdate(final Criteria criteria, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeUpdates(final LocationListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeUpdates(final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void addProximityAlert(final double latitude, final double longitude, final float radius, final long expiration, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeProximityAlert(final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isProviderEnabled(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public Location getLastKnownLocation(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public void addTestProvider(final String name, final boolean requiresNetwork, final boolean requiresSatellite, final boolean requiresCell, final boolean hasMonetaryCost, final boolean supportsAltitude, final boolean supportsSpeed, final boolean supportsBearing, final int powerRequirement, final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeTestProvider(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTestProviderLocation(final String provider, final Location loc) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearTestProviderLocation(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTestProviderEnabled(final String provider, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearTestProviderEnabled(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTestProviderStatus(final String provider, final int status, final Bundle extras, final long updateTime) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearTestProviderStatus(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean addGpsStatusListener(final GpsStatus.Listener listener) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeGpsStatusListener(final GpsStatus.Listener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerGnssStatusCallback(final GnssStatus.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerGnssStatusCallback(final GnssStatus.Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterGnssStatusCallback(final GnssStatus.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean addNmeaListener(final GpsStatus.NmeaListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeNmeaListener(final GpsStatus.NmeaListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addNmeaListener(final OnNmeaMessageListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addNmeaListener(final OnNmeaMessageListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeNmeaListener(final OnNmeaMessageListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerGnssMeasurementsCallback(final GnssMeasurementsEvent.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerGnssMeasurementsCallback(final GnssMeasurementsEvent.Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterGnssMeasurementsCallback(final GnssMeasurementsEvent.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerGnssNavigationMessageCallback(final GnssNavigationMessage.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerGnssNavigationMessageCallback(final GnssNavigationMessage.Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterGnssNavigationMessageCallback(final GnssNavigationMessage.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public GpsStatus getGpsStatus(final GpsStatus status) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean sendExtraCommand(final String provider, final String command, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
}
