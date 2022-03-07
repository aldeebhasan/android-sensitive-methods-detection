package android.content;

import android.os.*;

public class SyncAdapterType implements Parcelable
{
    public static final Creator<SyncAdapterType> CREATOR;
    public final String accountType;
    public final String authority;
    public final boolean isKey;
    
    public SyncAdapterType(final String authority, final String accountType, final boolean userVisible, final boolean supportsUploading) {
        throw new RuntimeException("Stub!");
    }
    
    public SyncAdapterType(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean supportsUploading() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean allowParallelSyncs() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAlwaysSyncable() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSettingsActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public static SyncAdapterType newKey(final String authority, final String accountType) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
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
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
