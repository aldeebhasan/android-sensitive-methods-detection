package android.view;

import android.graphics.*;
import android.util.*;
import android.os.*;

public final class Display
{
    public static final int DEFAULT_DISPLAY = 0;
    public static final int FLAG_PRESENTATION = 8;
    public static final int FLAG_PRIVATE = 4;
    public static final int FLAG_ROUND = 16;
    public static final int FLAG_SECURE = 2;
    public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 1;
    public static final int INVALID_DISPLAY = -1;
    public static final int STATE_DOZE = 3;
    public static final int STATE_DOZE_SUSPEND = 4;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 2;
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_VR = 5;
    
    Display() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDisplayId() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isValid() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public void getSize(final Point outSize) {
        throw new RuntimeException("Stub!");
    }
    
    public void getRectSize(final Rect outSize) {
        throw new RuntimeException("Stub!");
    }
    
    public void getCurrentSizeRange(final Point outSmallestSize, final Point outLargestSize) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRotation() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getOrientation() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getPixelFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public float getRefreshRate() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public float[] getSupportedRefreshRates() {
        throw new RuntimeException("Stub!");
    }
    
    public Mode getMode() {
        throw new RuntimeException("Stub!");
    }
    
    public Mode[] getSupportedModes() {
        throw new RuntimeException("Stub!");
    }
    
    public HdrCapabilities getHdrCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isHdr() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isWideColorGamut() {
        throw new RuntimeException("Stub!");
    }
    
    public long getAppVsyncOffsetNanos() {
        throw new RuntimeException("Stub!");
    }
    
    public long getPresentationDeadlineNanos() {
        throw new RuntimeException("Stub!");
    }
    
    public void getMetrics(final DisplayMetrics outMetrics) {
        throw new RuntimeException("Stub!");
    }
    
    public void getRealSize(final Point outSize) {
        throw new RuntimeException("Stub!");
    }
    
    public void getRealMetrics(final DisplayMetrics outMetrics) {
        throw new RuntimeException("Stub!");
    }
    
    public int getState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
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
}
