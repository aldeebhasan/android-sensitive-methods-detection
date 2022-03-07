package android.view;

import android.os.*;

public static final class Mode implements Parcelable
{
    public static final Creator<Mode> CREATOR;
    
    Mode() {
        throw new RuntimeException("Stub!");
    }
    
    public int getModeId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPhysicalWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPhysicalHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public float getRefreshRate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object other) {
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
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
