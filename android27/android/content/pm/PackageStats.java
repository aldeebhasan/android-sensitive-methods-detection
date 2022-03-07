package android.content.pm;

import android.os.*;

@Deprecated
public class PackageStats implements Parcelable
{
    public static final Creator<PackageStats> CREATOR;
    public long cacheSize;
    public long codeSize;
    public long dataSize;
    public long externalCacheSize;
    public long externalCodeSize;
    public long externalDataSize;
    public long externalMediaSize;
    public long externalObbSize;
    public String packageName;
    
    public PackageStats(final String pkgName) {
        throw new RuntimeException("Stub!");
    }
    
    public PackageStats(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    public PackageStats(final PackageStats pStats) {
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
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
