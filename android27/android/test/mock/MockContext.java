package android.test.mock;

import android.content.pm.*;
import android.database.sqlite.*;
import android.database.*;
import android.graphics.drawable.*;
import android.graphics.*;
import java.io.*;
import android.os.*;
import android.content.*;
import android.net.*;
import android.content.res.*;
import android.view.*;

public class MockContext extends Context
{
    public MockContext() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AssetManager getAssets() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Resources getResources() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PackageManager getPackageManager() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ContentResolver getContentResolver() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Looper getMainLooper() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Context getApplicationContext() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTheme(final int resid) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Resources.Theme getTheme() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ClassLoader getClassLoader() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ApplicationInfo getApplicationInfo() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getPackageResourcePath() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getPackageCodePath() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public SharedPreferences getSharedPreferences(final String name, final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean moveSharedPreferencesFrom(final Context sourceContext, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteSharedPreferences(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public FileInputStream openFileInput(final String name) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public FileOutputStream openFileOutput(final String name, final int mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteFile(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getFileStreamPath(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] fileList() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getDataDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getFilesDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getNoBackupFilesDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getExternalFilesDir(final String type) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getObbDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getCacheDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getCodeCacheDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getExternalCacheDir() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getDir(final String name, final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public SQLiteDatabase openOrCreateDatabase(final String file, final int mode, final SQLiteDatabase.CursorFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public SQLiteDatabase openOrCreateDatabase(final String file, final int mode, final SQLiteDatabase.CursorFactory factory, final DatabaseErrorHandler errorHandler) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getDatabasePath(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] databaseList() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean moveDatabaseFrom(final Context sourceContext, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteDatabase(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getWallpaper() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable peekWallpaper() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getWallpaperDesiredMinimumWidth() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getWallpaperDesiredMinimumHeight() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setWallpaper(final Bitmap bitmap) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setWallpaper(final InputStream data) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void clearWallpaper() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivity(final Intent intent, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivities(final Intent[] intents) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivities(final Intent[] intents, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startIntentSender(final IntentSender intent, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startIntentSender(final IntentSender intent, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendBroadcast(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendBroadcast(final Intent intent, final String receiverPermission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendOrderedBroadcast(final Intent intent, final String receiverPermission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendOrderedBroadcast(final Intent intent, final String receiverPermission, final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode, final String initialData, final Bundle initialExtras) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendBroadcastAsUser(final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendBroadcastAsUser(final Intent intent, final UserHandle user, final String receiverPermission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendOrderedBroadcastAsUser(final Intent intent, final UserHandle user, final String receiverPermission, final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode, final String initialData, final Bundle initialExtras) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendStickyBroadcast(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendStickyOrderedBroadcast(final Intent intent, final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode, final String initialData, final Bundle initialExtras) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void removeStickyBroadcast(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendStickyBroadcastAsUser(final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendStickyOrderedBroadcastAsUser(final Intent intent, final UserHandle user, final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode, final String initialData, final Bundle initialExtras) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void removeStickyBroadcastAsUser(final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter, final String broadcastPermission, final Handler scheduler) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter, final String broadcastPermission, final Handler scheduler, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void unregisterReceiver(final BroadcastReceiver receiver) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ComponentName startService(final Intent service) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ComponentName startForegroundService(final Intent service) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean stopService(final Intent service) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean bindService(final Intent service, final ServiceConnection conn, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void unbindService(final ServiceConnection conn) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean startInstrumentation(final ComponentName className, final String profileFile, final Bundle arguments) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Object getSystemService(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getSystemServiceName(final Class<?> serviceClass) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkPermission(final String permission, final int pid, final int uid) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkCallingPermission(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkCallingOrSelfPermission(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkSelfPermission(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforcePermission(final String permission, final int pid, final int uid, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforceCallingPermission(final String permission, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforceCallingOrSelfPermission(final String permission, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void grantUriPermission(final String toPackage, final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void revokeUriPermission(final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void revokeUriPermission(final String targetPackage, final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkUriPermission(final Uri uri, final int pid, final int uid, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkCallingUriPermission(final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkCallingOrSelfUriPermission(final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkUriPermission(final Uri uri, final String readPermission, final String writePermission, final int pid, final int uid, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforceUriPermission(final Uri uri, final int pid, final int uid, final int modeFlags, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforceCallingUriPermission(final Uri uri, final int modeFlags, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforceCallingOrSelfUriPermission(final Uri uri, final int modeFlags, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void enforceUriPermission(final Uri uri, final String readPermission, final String writePermission, final int pid, final int uid, final int modeFlags, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Context createPackageContext(final String packageName, final int flags) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Context createConfigurationContext(final Configuration overrideConfiguration) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Context createDisplayContext(final Display display) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isRestricted() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File[] getExternalFilesDirs(final String type) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File[] getObbDirs() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File[] getExternalCacheDirs() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File[] getExternalMediaDirs() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Context createDeviceProtectedStorageContext() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isDeviceProtectedStorage() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Context createContextForSplit(final String splitName) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
}
