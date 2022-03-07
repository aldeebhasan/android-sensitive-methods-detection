package android.location;

import java.util.*;
import android.os.*;

public final class GnssMeasurementsEvent implements Parcelable
{
    public static final Creator<GnssMeasurementsEvent> CREATOR;
    
    GnssMeasurementsEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public GnssClock getClock() {
        throw new RuntimeException("Stub!");
    }
    
    public Collection<GnssMeasurement> getMeasurements() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
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
}
