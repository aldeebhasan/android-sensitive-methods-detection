package android.content.pm;

import java.util.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.content.*;
import android.appwidget.*;
import android.os.*;

public class LauncherApps
{
    public static final String ACTION_CONFIRM_PIN_APPWIDGET = "android.content.pm.action.CONFIRM_PIN_APPWIDGET";
    public static final String ACTION_CONFIRM_PIN_SHORTCUT = "android.content.pm.action.CONFIRM_PIN_SHORTCUT";
    public static final String EXTRA_PIN_ITEM_REQUEST = "android.content.pm.extra.PIN_ITEM_REQUEST";
    
    LauncherApps() {
        throw new RuntimeException("Stub!");
    }
    
    public List<UserHandle> getProfiles() {
        throw new RuntimeException("Stub!");
    }
    
    public List<LauncherActivityInfo> getActivityList(final String packageName, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public LauncherActivityInfo resolveActivity(final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public void startMainActivity(final ComponentName component, final UserHandle user, final Rect sourceBounds, final Bundle opts) {
        throw new RuntimeException("Stub!");
    }
    
    public void startAppDetailsActivity(final ComponentName component, final UserHandle user, final Rect sourceBounds, final Bundle opts) {
        throw new RuntimeException("Stub!");
    }
    
    public List<LauncherActivityInfo> getShortcutConfigActivityList(final String packageName, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public IntentSender getShortcutConfigActivityIntent(final LauncherActivityInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPackageEnabled(final String packageName, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public ApplicationInfo getApplicationInfo(final String packageName, final int flags, final UserHandle user) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActivityEnabled(final ComponentName component, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasShortcutHostPermission() {
        throw new RuntimeException("Stub!");
    }
    
    public List<ShortcutInfo> getShortcuts(final ShortcutQuery query, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public void pinShortcuts(final String packageName, final List<String> shortcutIds, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getShortcutIconDrawable(final ShortcutInfo shortcut, final int density) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getShortcutBadgedIconDrawable(final ShortcutInfo shortcut, final int density) {
        throw new RuntimeException("Stub!");
    }
    
    public void startShortcut(final String packageName, final String shortcutId, final Rect sourceBounds, final Bundle startActivityOptions, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public void startShortcut(final ShortcutInfo shortcut, final Rect sourceBounds, final Bundle startActivityOptions) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterCallback(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public PinItemRequest getPinItemRequest(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onPackageRemoved(final String p0, final UserHandle p1);
        
        public abstract void onPackageAdded(final String p0, final UserHandle p1);
        
        public abstract void onPackageChanged(final String p0, final UserHandle p1);
        
        public abstract void onPackagesAvailable(final String[] p0, final UserHandle p1, final boolean p2);
        
        public abstract void onPackagesUnavailable(final String[] p0, final UserHandle p1, final boolean p2);
        
        public void onPackagesSuspended(final String[] packageNames, final UserHandle user) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPackagesUnsuspended(final String[] packageNames, final UserHandle user) {
            throw new RuntimeException("Stub!");
        }
        
        public void onShortcutsChanged(final String packageName, final List<ShortcutInfo> shortcuts, final UserHandle user) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class ShortcutQuery
    {
        public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;
        public static final int FLAG_MATCH_DYNAMIC = 1;
        public static final int FLAG_MATCH_MANIFEST = 8;
        public static final int FLAG_MATCH_PINNED = 2;
        
        public ShortcutQuery() {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutQuery setChangedSince(final long changedSince) {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutQuery setPackage(final String packageName) {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutQuery setShortcutIds(final List<String> shortcutIds) {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutQuery setActivity(final ComponentName activity) {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutQuery setQueryFlags(final int queryFlags) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class PinItemRequest implements Parcelable
    {
        public static final Creator<PinItemRequest> CREATOR;
        public static final int REQUEST_TYPE_APPWIDGET = 2;
        public static final int REQUEST_TYPE_SHORTCUT = 1;
        
        PinItemRequest() {
            throw new RuntimeException("Stub!");
        }
        
        public int getRequestType() {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutInfo getShortcutInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public AppWidgetProviderInfo getAppWidgetProviderInfo(final Context context) {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isValid() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean accept(final Bundle options) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean accept() {
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
}
