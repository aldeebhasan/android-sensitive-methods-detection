package android.content.pm;

import android.os.*;

public final class VersionedPackage implements Parcelable
{
    public static final Creator<VersionedPackage> CREATOR;
    
    public VersionedPackage(final String packageName, final int versionCode) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getVersionCode() {
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
