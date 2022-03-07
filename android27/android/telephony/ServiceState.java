package android.telephony;

import android.os.*;

public class ServiceState implements Parcelable
{
    public static final Creator<ServiceState> CREATOR;
    public static final int STATE_EMERGENCY_ONLY = 2;
    public static final int STATE_IN_SERVICE = 0;
    public static final int STATE_OUT_OF_SERVICE = 1;
    public static final int STATE_POWER_OFF = 3;
    
    public ServiceState() {
        throw new RuntimeException("Stub!");
    }
    
    public ServiceState(final ServiceState s) {
        throw new RuntimeException("Stub!");
    }
    
    public ServiceState(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    protected void copyFrom(final ServiceState s) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public int getState() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getRoaming() {
        throw new RuntimeException("Stub!");
    }
    
    public String getOperatorAlphaLong() {
        throw new RuntimeException("Stub!");
    }
    
    public String getOperatorAlphaShort() {
        throw new RuntimeException("Stub!");
    }
    
    public String getOperatorNumeric() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getIsManualSelection() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStateOutOfService() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStateOff() {
        throw new RuntimeException("Stub!");
    }
    
    public void setState(final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRoaming(final boolean roaming) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOperatorName(final String longName, final String shortName, final String numeric) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIsManualSelection(final boolean isManual) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
