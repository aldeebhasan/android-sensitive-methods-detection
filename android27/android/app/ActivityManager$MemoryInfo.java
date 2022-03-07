package android.app;

import android.os.*;

public static class MemoryInfo implements Parcelable
{
    public static final Creator<MemoryInfo> CREATOR;
    public long availMem;
    public boolean lowMemory;
    public long threshold;
    public long totalMem;
    
    public MemoryInfo() {
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
    
    public void readFromParcel(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
