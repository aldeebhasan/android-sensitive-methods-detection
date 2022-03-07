package android.app.usage;

import android.os.*;

public final class StorageStats implements Parcelable
{
    public static final Creator<StorageStats> CREATOR;
    
    StorageStats() {
        throw new RuntimeException("Stub!");
    }
    
    public long getAppBytes() {
        throw new RuntimeException("Stub!");
    }
    
    public long getDataBytes() {
        throw new RuntimeException("Stub!");
    }
    
    public long getCacheBytes() {
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
