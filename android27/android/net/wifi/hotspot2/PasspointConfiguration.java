package android.net.wifi.hotspot2;

import android.net.wifi.hotspot2.pps.*;
import android.os.*;

public final class PasspointConfiguration implements Parcelable
{
    public static final Creator<PasspointConfiguration> CREATOR;
    
    public PasspointConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public PasspointConfiguration(final PasspointConfiguration source) {
        throw new RuntimeException("Stub!");
    }
    
    public void setHomeSp(final HomeSp homeSp) {
        throw new RuntimeException("Stub!");
    }
    
    public HomeSp getHomeSp() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCredential(final Credential credential) {
        throw new RuntimeException("Stub!");
    }
    
    public Credential getCredential() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object thatObject) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
