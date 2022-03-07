package android.hardware;

import android.os.*;

public final class HardwareBuffer implements Parcelable, AutoCloseable
{
    public static final int BLOB = 33;
    public static final Creator<HardwareBuffer> CREATOR;
    public static final int RGBA_1010102 = 43;
    public static final int RGBA_8888 = 1;
    public static final int RGBA_FP16 = 22;
    public static final int RGBX_8888 = 2;
    public static final int RGB_565 = 4;
    public static final int RGB_888 = 3;
    public static final long USAGE_CPU_READ_OFTEN = 3L;
    public static final long USAGE_CPU_READ_RARELY = 2L;
    public static final long USAGE_CPU_WRITE_OFTEN = 48L;
    public static final long USAGE_CPU_WRITE_RARELY = 32L;
    public static final long USAGE_GPU_COLOR_OUTPUT = 512L;
    public static final long USAGE_GPU_DATA_BUFFER = 16777216L;
    public static final long USAGE_GPU_SAMPLED_IMAGE = 256L;
    public static final long USAGE_PROTECTED_CONTENT = 16384L;
    public static final long USAGE_SENSOR_DIRECT_DATA = 8388608L;
    public static final long USAGE_VIDEO_ENCODE = 65536L;
    
    HardwareBuffer() {
        throw new RuntimeException("Stub!");
    }
    
    public static HardwareBuffer create(final int width, final int height, final int format, final int layers, final long usage) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLayers() {
        throw new RuntimeException("Stub!");
    }
    
    public long getUsage() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isClosed() {
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
    
    static {
        CREATOR = null;
    }
}
