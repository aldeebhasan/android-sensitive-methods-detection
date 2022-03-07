package android.telecom;

import android.os.*;

public static final class CameraCapabilities implements Parcelable
{
    public static final Creator<CameraCapabilities> CREATOR;
    
    public CameraCapabilities(final int width, final int height) {
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
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
