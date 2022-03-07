package android.os;

import java.util.*;

public static class MemoryInfo implements Parcelable
{
    public static final Creator<MemoryInfo> CREATOR;
    public int dalvikPrivateDirty;
    public int dalvikPss;
    public int dalvikSharedDirty;
    public int nativePrivateDirty;
    public int nativePss;
    public int nativeSharedDirty;
    public int otherPrivateDirty;
    public int otherPss;
    public int otherSharedDirty;
    
    public MemoryInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalPss() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalSwappablePss() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalPrivateDirty() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalSharedDirty() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalPrivateClean() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalSharedClean() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMemoryStat(final String statName) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<String, String> getMemoryStats() {
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
