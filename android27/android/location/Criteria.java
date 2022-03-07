package android.location;

import android.os.*;

public class Criteria implements Parcelable
{
    public static final int ACCURACY_COARSE = 2;
    public static final int ACCURACY_FINE = 1;
    public static final int ACCURACY_HIGH = 3;
    public static final int ACCURACY_LOW = 1;
    public static final int ACCURACY_MEDIUM = 2;
    public static final Creator<Criteria> CREATOR;
    public static final int NO_REQUIREMENT = 0;
    public static final int POWER_HIGH = 3;
    public static final int POWER_LOW = 1;
    public static final int POWER_MEDIUM = 2;
    
    public Criteria() {
        throw new RuntimeException("Stub!");
    }
    
    public Criteria(final Criteria criteria) {
        throw new RuntimeException("Stub!");
    }
    
    public void setHorizontalAccuracy(final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHorizontalAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVerticalAccuracy(final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    public int getVerticalAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSpeedAccuracy(final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSpeedAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBearingAccuracy(final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    public int getBearingAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAccuracy(final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    public int getAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPowerRequirement(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPowerRequirement() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCostAllowed(final boolean costAllowed) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isCostAllowed() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAltitudeRequired(final boolean altitudeRequired) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAltitudeRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSpeedRequired(final boolean speedRequired) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSpeedRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBearingRequired(final boolean bearingRequired) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isBearingRequired() {
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
}
