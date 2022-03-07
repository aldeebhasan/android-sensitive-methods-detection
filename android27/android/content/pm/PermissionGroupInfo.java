package android.content.pm;

import android.os.*;

public class PermissionGroupInfo extends PackageItemInfo implements Parcelable
{
    public static final Creator<PermissionGroupInfo> CREATOR;
    public static final int FLAG_PERSONAL_INFO = 1;
    public int descriptionRes;
    public int flags;
    public CharSequence nonLocalizedDescription;
    public int priority;
    
    public PermissionGroupInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public PermissionGroupInfo(final PermissionGroupInfo orig) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadDescription(final PackageManager pm) {
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
    
    static {
        CREATOR = null;
    }
}
