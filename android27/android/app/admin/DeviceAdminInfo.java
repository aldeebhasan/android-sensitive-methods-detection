package android.app.admin;

import org.xmlpull.v1.*;
import java.io.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.util.*;
import android.os.*;

public final class DeviceAdminInfo implements Parcelable
{
    public static final Creator<DeviceAdminInfo> CREATOR;
    public static final int USES_ENCRYPTED_STORAGE = 7;
    public static final int USES_POLICY_DISABLE_CAMERA = 8;
    public static final int USES_POLICY_DISABLE_KEYGUARD_FEATURES = 9;
    public static final int USES_POLICY_EXPIRE_PASSWORD = 6;
    public static final int USES_POLICY_FORCE_LOCK = 3;
    public static final int USES_POLICY_LIMIT_PASSWORD = 0;
    public static final int USES_POLICY_RESET_PASSWORD = 2;
    public static final int USES_POLICY_WATCH_LOGIN = 1;
    public static final int USES_POLICY_WIPE_DATA = 4;
    
    public DeviceAdminInfo(final Context context, final ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getReceiverName() {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityInfo getActivityInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getComponent() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadLabel(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadDescription(final PackageManager pm) throws Resources.NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadIcon(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean usesPolicy(final int policyIdent) {
        throw new RuntimeException("Stub!");
    }
    
    public String getTagForPolicy(final int policyIdent) {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
