package android.content.pm;

import android.graphics.*;
import java.util.*;
import java.io.*;
import android.net.*;
import android.os.*;
import android.content.*;

public class PackageInstaller
{
    public static final String ACTION_SESSION_COMMITTED = "android.content.pm.action.SESSION_COMMITTED";
    public static final String ACTION_SESSION_DETAILS = "android.content.pm.action.SESSION_DETAILS";
    public static final String EXTRA_OTHER_PACKAGE_NAME = "android.content.pm.extra.OTHER_PACKAGE_NAME";
    public static final String EXTRA_PACKAGE_NAME = "android.content.pm.extra.PACKAGE_NAME";
    public static final String EXTRA_SESSION = "android.content.pm.extra.SESSION";
    public static final String EXTRA_SESSION_ID = "android.content.pm.extra.SESSION_ID";
    public static final String EXTRA_STATUS = "android.content.pm.extra.STATUS";
    public static final String EXTRA_STATUS_MESSAGE = "android.content.pm.extra.STATUS_MESSAGE";
    public static final String EXTRA_STORAGE_PATH = "android.content.pm.extra.STORAGE_PATH";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_FAILURE_ABORTED = 3;
    public static final int STATUS_FAILURE_BLOCKED = 2;
    public static final int STATUS_FAILURE_CONFLICT = 5;
    public static final int STATUS_FAILURE_INCOMPATIBLE = 7;
    public static final int STATUS_FAILURE_INVALID = 4;
    public static final int STATUS_FAILURE_STORAGE = 6;
    public static final int STATUS_PENDING_USER_ACTION = -1;
    public static final int STATUS_SUCCESS = 0;
    
    PackageInstaller() {
        throw new RuntimeException("Stub!");
    }
    
    public int createSession(final SessionParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public Session openSession(final int sessionId) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void updateSessionAppIcon(final int sessionId, final Bitmap appIcon) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateSessionAppLabel(final int sessionId, final CharSequence appLabel) {
        throw new RuntimeException("Stub!");
    }
    
    public void abandonSession(final int sessionId) {
        throw new RuntimeException("Stub!");
    }
    
    public SessionInfo getSessionInfo(final int sessionId) {
        throw new RuntimeException("Stub!");
    }
    
    public List<SessionInfo> getAllSessions() {
        throw new RuntimeException("Stub!");
    }
    
    public List<SessionInfo> getMySessions() {
        throw new RuntimeException("Stub!");
    }
    
    public void uninstall(final String packageName, final IntentSender statusReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    public void uninstall(final VersionedPackage versionedPackage, final IntentSender statusReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerSessionCallback(final SessionCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerSessionCallback(final SessionCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterSessionCallback(final SessionCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class SessionCallback
    {
        public SessionCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onCreated(final int p0);
        
        public abstract void onBadgingChanged(final int p0);
        
        public abstract void onActiveChanged(final int p0, final boolean p1);
        
        public abstract void onProgressChanged(final int p0, final float p1);
        
        public abstract void onFinished(final int p0, final boolean p1);
    }
    
    public static class Session implements Closeable
    {
        Session() {
            throw new RuntimeException("Stub!");
        }
        
        public void setStagingProgress(final float progress) {
            throw new RuntimeException("Stub!");
        }
        
        public OutputStream openWrite(final String name, final long offsetBytes, final long lengthBytes) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        public void fsync(final OutputStream out) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        public String[] getNames() throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        public InputStream openRead(final String name) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        public void removeSplit(final String splitName) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        public void commit(final IntentSender statusReceiver) {
            throw new RuntimeException("Stub!");
        }
        
        public void transfer(final String packageName) throws PackageManager.NameNotFoundException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void close() {
            throw new RuntimeException("Stub!");
        }
        
        public void abandon() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class SessionParams implements Parcelable
    {
        public static final Creator<SessionParams> CREATOR;
        public static final int MODE_FULL_INSTALL = 1;
        public static final int MODE_INHERIT_EXISTING = 2;
        
        public SessionParams(final int mode) {
            throw new RuntimeException("Stub!");
        }
        
        public void setInstallLocation(final int installLocation) {
            throw new RuntimeException("Stub!");
        }
        
        public void setSize(final long sizeBytes) {
            throw new RuntimeException("Stub!");
        }
        
        public void setAppPackageName(final String appPackageName) {
            throw new RuntimeException("Stub!");
        }
        
        public void setAppIcon(final Bitmap appIcon) {
            throw new RuntimeException("Stub!");
        }
        
        public void setAppLabel(final CharSequence appLabel) {
            throw new RuntimeException("Stub!");
        }
        
        public void setOriginatingUri(final Uri originatingUri) {
            throw new RuntimeException("Stub!");
        }
        
        public void setOriginatingUid(final int originatingUid) {
            throw new RuntimeException("Stub!");
        }
        
        public void setReferrerUri(final Uri referrerUri) {
            throw new RuntimeException("Stub!");
        }
        
        public void setInstallReason(final int installReason) {
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
    
    public static class SessionInfo implements Parcelable
    {
        public static final Creator<SessionInfo> CREATOR;
        
        SessionInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSessionId() {
            throw new RuntimeException("Stub!");
        }
        
        public String getInstallerPackageName() {
            throw new RuntimeException("Stub!");
        }
        
        public float getProgress() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isActive() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isSealed() {
            throw new RuntimeException("Stub!");
        }
        
        public int getInstallReason() {
            throw new RuntimeException("Stub!");
        }
        
        public String getAppPackageName() {
            throw new RuntimeException("Stub!");
        }
        
        public Bitmap getAppIcon() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getAppLabel() {
            throw new RuntimeException("Stub!");
        }
        
        public Intent createDetailsIntent() {
            throw new RuntimeException("Stub!");
        }
        
        public int getMode() {
            throw new RuntimeException("Stub!");
        }
        
        public int getInstallLocation() {
            throw new RuntimeException("Stub!");
        }
        
        public long getSize() {
            throw new RuntimeException("Stub!");
        }
        
        public Uri getOriginatingUri() {
            throw new RuntimeException("Stub!");
        }
        
        public int getOriginatingUid() {
            throw new RuntimeException("Stub!");
        }
        
        public Uri getReferrerUri() {
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
}
