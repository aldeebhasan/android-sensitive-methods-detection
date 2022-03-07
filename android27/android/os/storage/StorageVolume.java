package android.os.storage;

import android.content.*;
import android.os.*;

public final class StorageVolume implements Parcelable
{
    public static final Creator<StorageVolume> CREATOR;
    public static final String EXTRA_STORAGE_VOLUME = "android.os.storage.extra.STORAGE_VOLUME";
    
    StorageVolume() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDescription(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPrimary() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRemovable() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEmulated() {
        throw new RuntimeException("Stub!");
    }
    
    public String getUuid() {
        throw new RuntimeException("Stub!");
    }
    
    public String getState() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createAccessIntent(final String directoryName) {
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
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
