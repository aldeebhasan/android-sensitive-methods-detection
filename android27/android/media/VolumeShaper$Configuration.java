package android.media;

import android.os.*;

public static final class Configuration implements Parcelable
{
    public static final Creator<Configuration> CREATOR;
    public static final Configuration CUBIC_RAMP;
    public static final int INTERPOLATOR_TYPE_CUBIC = 2;
    public static final int INTERPOLATOR_TYPE_CUBIC_MONOTONIC = 3;
    public static final int INTERPOLATOR_TYPE_LINEAR = 1;
    public static final int INTERPOLATOR_TYPE_STEP = 0;
    public static final Configuration LINEAR_RAMP;
    public static final Configuration SCURVE_RAMP;
    public static final Configuration SINE_RAMP;
    
    Configuration() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getMaximumCurvePoints() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public int getInterpolatorType() {
        throw new RuntimeException("Stub!");
    }
    
    public long getDuration() {
        throw new RuntimeException("Stub!");
    }
    
    public float[] getTimes() {
        throw new RuntimeException("Stub!");
    }
    
    public float[] getVolumes() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
        CUBIC_RAMP = null;
        LINEAR_RAMP = null;
        SCURVE_RAMP = null;
        SINE_RAMP = null;
    }
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final Configuration configuration) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setInterpolatorType(final int interpolatorType) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDuration(final long durationMillis) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setCurve(final float[] times, final float[] volumes) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder reflectTimes() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder invertVolumes() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder scaleToEndVolume(final float volume) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder scaleToStartVolume(final float volume) {
            throw new RuntimeException("Stub!");
        }
        
        public Configuration build() {
            throw new RuntimeException("Stub!");
        }
    }
}
