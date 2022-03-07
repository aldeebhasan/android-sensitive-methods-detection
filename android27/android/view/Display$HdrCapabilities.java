package android.view;

import android.os.*;

public static final class HdrCapabilities implements Parcelable
{
    public static final Creator<HdrCapabilities> CREATOR;
    public static final int HDR_TYPE_DOLBY_VISION = 1;
    public static final int HDR_TYPE_HDR10 = 2;
    public static final int HDR_TYPE_HLG = 3;
    public static final float INVALID_LUMINANCE = -1.0f;
    
    HdrCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getSupportedHdrTypes() {
        throw new RuntimeException("Stub!");
    }
    
    public float getDesiredMaxLuminance() {
        throw new RuntimeException("Stub!");
    }
    
    public float getDesiredMaxAverageLuminance() {
        throw new RuntimeException("Stub!");
    }
    
    public float getDesiredMinLuminance() {
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
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
