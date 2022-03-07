package android.content.pm;

import android.util.*;
import android.os.*;

public final class ProviderInfo extends ComponentInfo implements Parcelable
{
    public static final Creator<ProviderInfo> CREATOR;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public String authority;
    public int flags;
    public boolean grantUriPermissions;
    public int initOrder;
    @Deprecated
    public boolean isSyncable;
    public boolean multiprocess;
    public PathPermission[] pathPermissions;
    public String readPermission;
    public PatternMatcher[] uriPermissionPatterns;
    public String writePermission;
    
    public ProviderInfo() {
        this.pathPermissions = null;
        this.uriPermissionPatterns = null;
        throw new RuntimeException("Stub!");
    }
    
    public ProviderInfo(final ProviderInfo orig) {
        this.pathPermissions = null;
        this.uriPermissionPatterns = null;
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
