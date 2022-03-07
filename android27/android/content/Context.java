package android.content;

import android.graphics.drawable.*;
import android.util.*;
import android.content.pm.*;
import android.database.sqlite.*;
import android.database.*;
import android.graphics.*;
import java.io.*;
import android.os.*;
import android.net.*;
import android.content.res.*;
import android.view.*;

public abstract class Context
{
    public static final String ACCESSIBILITY_SERVICE = "accessibility";
    public static final String ACCOUNT_SERVICE = "account";
    public static final String ACTIVITY_SERVICE = "activity";
    public static final String ALARM_SERVICE = "alarm";
    public static final String APPWIDGET_SERVICE = "appwidget";
    public static final String APP_OPS_SERVICE = "appops";
    public static final String AUDIO_SERVICE = "audio";
    public static final String BATTERY_SERVICE = "batterymanager";
    public static final int BIND_ABOVE_CLIENT = 8;
    public static final int BIND_ADJUST_WITH_ACTIVITY = 128;
    public static final int BIND_ALLOW_OOM_MANAGEMENT = 16;
    public static final int BIND_AUTO_CREATE = 1;
    public static final int BIND_DEBUG_UNBIND = 2;
    public static final int BIND_EXTERNAL_SERVICE = Integer.MIN_VALUE;
    public static final int BIND_IMPORTANT = 64;
    public static final int BIND_NOT_FOREGROUND = 4;
    public static final int BIND_WAIVE_PRIORITY = 32;
    public static final String BLUETOOTH_SERVICE = "bluetooth";
    public static final String CAMERA_SERVICE = "camera";
    public static final String CAPTIONING_SERVICE = "captioning";
    public static final String CARRIER_CONFIG_SERVICE = "carrier_config";
    public static final String CLIPBOARD_SERVICE = "clipboard";
    public static final String COMPANION_DEVICE_SERVICE = "companiondevice";
    public static final String CONNECTIVITY_SERVICE = "connectivity";
    public static final String CONSUMER_IR_SERVICE = "consumer_ir";
    public static final int CONTEXT_IGNORE_SECURITY = 2;
    public static final int CONTEXT_INCLUDE_CODE = 1;
    public static final int CONTEXT_RESTRICTED = 4;
    public static final String DEVICE_POLICY_SERVICE = "device_policy";
    public static final String DISPLAY_SERVICE = "display";
    public static final String DOWNLOAD_SERVICE = "download";
    public static final String DROPBOX_SERVICE = "dropbox";
    public static final String FINGERPRINT_SERVICE = "fingerprint";
    public static final String HARDWARE_PROPERTIES_SERVICE = "hardware_properties";
    public static final String INPUT_METHOD_SERVICE = "input_method";
    public static final String INPUT_SERVICE = "input";
    public static final String JOB_SCHEDULER_SERVICE = "jobscheduler";
    public static final String KEYGUARD_SERVICE = "keyguard";
    public static final String LAUNCHER_APPS_SERVICE = "launcherapps";
    public static final String LAYOUT_INFLATER_SERVICE = "layout_inflater";
    public static final String LOCATION_SERVICE = "location";
    public static final String MEDIA_PROJECTION_SERVICE = "media_projection";
    public static final String MEDIA_ROUTER_SERVICE = "media_router";
    public static final String MEDIA_SESSION_SERVICE = "media_session";
    public static final String MIDI_SERVICE = "midi";
    public static final int MODE_APPEND = 32768;
    public static final int MODE_ENABLE_WRITE_AHEAD_LOGGING = 8;
    @Deprecated
    public static final int MODE_MULTI_PROCESS = 4;
    public static final int MODE_NO_LOCALIZED_COLLATORS = 16;
    public static final int MODE_PRIVATE = 0;
    @Deprecated
    public static final int MODE_WORLD_READABLE = 1;
    @Deprecated
    public static final int MODE_WORLD_WRITEABLE = 2;
    public static final String NETWORK_STATS_SERVICE = "netstats";
    public static final String NFC_SERVICE = "nfc";
    public static final String NOTIFICATION_SERVICE = "notification";
    public static final String NSD_SERVICE = "servicediscovery";
    public static final String POWER_SERVICE = "power";
    public static final String PRINT_SERVICE = "print";
    public static final int RECEIVER_VISIBLE_TO_INSTANT_APPS = 1;
    public static final String RESTRICTIONS_SERVICE = "restrictions";
    public static final String SEARCH_SERVICE = "search";
    public static final String SENSOR_SERVICE = "sensor";
    public static final String SHORTCUT_SERVICE = "shortcut";
    public static final String STORAGE_SERVICE = "storage";
    public static final String STORAGE_STATS_SERVICE = "storagestats";
    public static final String SYSTEM_HEALTH_SERVICE = "systemhealth";
    public static final String TELECOM_SERVICE = "telecom";
    public static final String TELEPHONY_SERVICE = "phone";
    public static final String TELEPHONY_SUBSCRIPTION_SERVICE = "telephony_subscription_service";
    public static final String TEXT_CLASSIFICATION_SERVICE = "textclassification";
    public static final String TEXT_SERVICES_MANAGER_SERVICE = "textservices";
    public static final String TV_INPUT_SERVICE = "tv_input";
    public static final String UI_MODE_SERVICE = "uimode";
    public static final String USAGE_STATS_SERVICE = "usagestats";
    public static final String USB_SERVICE = "usb";
    public static final String USER_SERVICE = "user";
    public static final String VIBRATOR_SERVICE = "vibrator";
    public static final String WALLPAPER_SERVICE = "wallpaper";
    public static final String WIFI_AWARE_SERVICE = "wifiaware";
    public static final String WIFI_P2P_SERVICE = "wifip2p";
    public static final String WIFI_SERVICE = "wifi";
    public static final String WINDOW_SERVICE = "window";
    
    public Context() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract AssetManager getAssets();
    
    public abstract Resources getResources();
    
    public abstract PackageManager getPackageManager();
    
    public abstract ContentResolver getContentResolver();
    
    public abstract Looper getMainLooper();
    
    public abstract Context getApplicationContext();
    
    public void registerComponentCallbacks(final ComponentCallbacks callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterComponentCallbacks(final ComponentCallbacks callback) {
        throw new RuntimeException("Stub!");
    }
    
    public final CharSequence getText(final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getString(final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getString(final int resId, final Object... formatArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getColor(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public final Drawable getDrawable(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public final ColorStateList getColorStateList(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void setTheme(final int p0);
    
    @ViewDebug.ExportedProperty(deepExport = true)
    public abstract Resources.Theme getTheme();
    
    public final TypedArray obtainStyledAttributes(final int[] attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public final TypedArray obtainStyledAttributes(final int resid, final int[] attrs) throws Resources.NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final TypedArray obtainStyledAttributes(final AttributeSet set, final int[] attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public final TypedArray obtainStyledAttributes(final AttributeSet set, final int[] attrs, final int defStyleAttr, final int defStyleRes) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract ClassLoader getClassLoader();
    
    public abstract String getPackageName();
    
    public abstract ApplicationInfo getApplicationInfo();
    
    public abstract String getPackageResourcePath();
    
    public abstract String getPackageCodePath();
    
    public abstract SharedPreferences getSharedPreferences(final String p0, final int p1);
    
    public abstract boolean moveSharedPreferencesFrom(final Context p0, final String p1);
    
    public abstract boolean deleteSharedPreferences(final String p0);
    
    public abstract FileInputStream openFileInput(final String p0) throws FileNotFoundException;
    
    public abstract FileOutputStream openFileOutput(final String p0, final int p1) throws FileNotFoundException;
    
    public abstract boolean deleteFile(final String p0);
    
    public abstract File getFileStreamPath(final String p0);
    
    public abstract File getDataDir();
    
    public abstract File getFilesDir();
    
    public abstract File getNoBackupFilesDir();
    
    public abstract File getExternalFilesDir(final String p0);
    
    public abstract File[] getExternalFilesDirs(final String p0);
    
    public abstract File getObbDir();
    
    public abstract File[] getObbDirs();
    
    public abstract File getCacheDir();
    
    public abstract File getCodeCacheDir();
    
    public abstract File getExternalCacheDir();
    
    public abstract File[] getExternalCacheDirs();
    
    public abstract File[] getExternalMediaDirs();
    
    public abstract String[] fileList();
    
    public abstract File getDir(final String p0, final int p1);
    
    public abstract SQLiteDatabase openOrCreateDatabase(final String p0, final int p1, final SQLiteDatabase.CursorFactory p2);
    
    public abstract SQLiteDatabase openOrCreateDatabase(final String p0, final int p1, final SQLiteDatabase.CursorFactory p2, final DatabaseErrorHandler p3);
    
    public abstract boolean moveDatabaseFrom(final Context p0, final String p1);
    
    public abstract boolean deleteDatabase(final String p0);
    
    public abstract File getDatabasePath(final String p0);
    
    public abstract String[] databaseList();
    
    @Deprecated
    public abstract Drawable getWallpaper();
    
    @Deprecated
    public abstract Drawable peekWallpaper();
    
    @Deprecated
    public abstract int getWallpaperDesiredMinimumWidth();
    
    @Deprecated
    public abstract int getWallpaperDesiredMinimumHeight();
    
    @Deprecated
    public abstract void setWallpaper(final Bitmap p0) throws IOException;
    
    @Deprecated
    public abstract void setWallpaper(final InputStream p0) throws IOException;
    
    @Deprecated
    public abstract void clearWallpaper() throws IOException;
    
    public abstract void startActivity(final Intent p0);
    
    public abstract void startActivity(final Intent p0, final Bundle p1);
    
    public abstract void startActivities(final Intent[] p0);
    
    public abstract void startActivities(final Intent[] p0, final Bundle p1);
    
    public abstract void startIntentSender(final IntentSender p0, final Intent p1, final int p2, final int p3, final int p4) throws IntentSender.SendIntentException;
    
    public abstract void startIntentSender(final IntentSender p0, final Intent p1, final int p2, final int p3, final int p4, final Bundle p5) throws IntentSender.SendIntentException;
    
    public abstract void sendBroadcast(final Intent p0);
    
    public abstract void sendBroadcast(final Intent p0, final String p1);
    
    public abstract void sendOrderedBroadcast(final Intent p0, final String p1);
    
    public abstract void sendOrderedBroadcast(final Intent p0, final String p1, final BroadcastReceiver p2, final Handler p3, final int p4, final String p5, final Bundle p6);
    
    public abstract void sendBroadcastAsUser(final Intent p0, final UserHandle p1);
    
    public abstract void sendBroadcastAsUser(final Intent p0, final UserHandle p1, final String p2);
    
    public abstract void sendOrderedBroadcastAsUser(final Intent p0, final UserHandle p1, final String p2, final BroadcastReceiver p3, final Handler p4, final int p5, final String p6, final Bundle p7);
    
    @Deprecated
    public abstract void sendStickyBroadcast(final Intent p0);
    
    @Deprecated
    public abstract void sendStickyOrderedBroadcast(final Intent p0, final BroadcastReceiver p1, final Handler p2, final int p3, final String p4, final Bundle p5);
    
    @Deprecated
    public abstract void removeStickyBroadcast(final Intent p0);
    
    @Deprecated
    public abstract void sendStickyBroadcastAsUser(final Intent p0, final UserHandle p1);
    
    @Deprecated
    public abstract void sendStickyOrderedBroadcastAsUser(final Intent p0, final UserHandle p1, final BroadcastReceiver p2, final Handler p3, final int p4, final String p5, final Bundle p6);
    
    @Deprecated
    public abstract void removeStickyBroadcastAsUser(final Intent p0, final UserHandle p1);
    
    public abstract Intent registerReceiver(final BroadcastReceiver p0, final IntentFilter p1);
    
    public abstract Intent registerReceiver(final BroadcastReceiver p0, final IntentFilter p1, final int p2);
    
    public abstract Intent registerReceiver(final BroadcastReceiver p0, final IntentFilter p1, final String p2, final Handler p3);
    
    public abstract Intent registerReceiver(final BroadcastReceiver p0, final IntentFilter p1, final String p2, final Handler p3, final int p4);
    
    public abstract void unregisterReceiver(final BroadcastReceiver p0);
    
    public abstract ComponentName startService(final Intent p0);
    
    public abstract ComponentName startForegroundService(final Intent p0);
    
    public abstract boolean stopService(final Intent p0);
    
    public abstract boolean bindService(final Intent p0, final ServiceConnection p1, final int p2);
    
    public abstract void unbindService(final ServiceConnection p0);
    
    public abstract boolean startInstrumentation(final ComponentName p0, final String p1, final Bundle p2);
    
    public abstract Object getSystemService(final String p0);
    
    public final <T> T getSystemService(final Class<T> serviceClass) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract String getSystemServiceName(final Class<?> p0);
    
    public abstract int checkPermission(final String p0, final int p1, final int p2);
    
    public abstract int checkCallingPermission(final String p0);
    
    public abstract int checkCallingOrSelfPermission(final String p0);
    
    public abstract int checkSelfPermission(final String p0);
    
    public abstract void enforcePermission(final String p0, final int p1, final int p2, final String p3);
    
    public abstract void enforceCallingPermission(final String p0, final String p1);
    
    public abstract void enforceCallingOrSelfPermission(final String p0, final String p1);
    
    public abstract void grantUriPermission(final String p0, final Uri p1, final int p2);
    
    public abstract void revokeUriPermission(final Uri p0, final int p1);
    
    public abstract void revokeUriPermission(final String p0, final Uri p1, final int p2);
    
    public abstract int checkUriPermission(final Uri p0, final int p1, final int p2, final int p3);
    
    public abstract int checkCallingUriPermission(final Uri p0, final int p1);
    
    public abstract int checkCallingOrSelfUriPermission(final Uri p0, final int p1);
    
    public abstract int checkUriPermission(final Uri p0, final String p1, final String p2, final int p3, final int p4, final int p5);
    
    public abstract void enforceUriPermission(final Uri p0, final int p1, final int p2, final int p3, final String p4);
    
    public abstract void enforceCallingUriPermission(final Uri p0, final int p1, final String p2);
    
    public abstract void enforceCallingOrSelfUriPermission(final Uri p0, final int p1, final String p2);
    
    public abstract void enforceUriPermission(final Uri p0, final String p1, final String p2, final int p3, final int p4, final int p5, final String p6);
    
    public abstract Context createPackageContext(final String p0, final int p1) throws PackageManager.NameNotFoundException;
    
    public abstract Context createContextForSplit(final String p0) throws PackageManager.NameNotFoundException;
    
    public abstract Context createConfigurationContext(final Configuration p0);
    
    public abstract Context createDisplayContext(final Display p0);
    
    public abstract Context createDeviceProtectedStorageContext();
    
    public boolean isRestricted() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean isDeviceProtectedStorage();
}
