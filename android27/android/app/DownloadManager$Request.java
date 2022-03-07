package android.app;

import android.net.*;
import android.content.*;

public static class Request
{
    public static final int NETWORK_MOBILE = 1;
    public static final int NETWORK_WIFI = 2;
    public static final int VISIBILITY_HIDDEN = 2;
    public static final int VISIBILITY_VISIBLE = 0;
    public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
    public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;
    
    public Request(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setDestinationUri(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setDestinationInExternalFilesDir(final Context context, final String dirType, final String subPath) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setDestinationInExternalPublicDir(final String dirType, final String subPath) {
        throw new RuntimeException("Stub!");
    }
    
    public void allowScanningByMediaScanner() {
        throw new RuntimeException("Stub!");
    }
    
    public Request addRequestHeader(final String header, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setDescription(final CharSequence description) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setMimeType(final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Request setShowRunningNotification(final boolean show) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setNotificationVisibility(final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setAllowedNetworkTypes(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setAllowedOverRoaming(final boolean allowed) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setAllowedOverMetered(final boolean allow) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setRequiresCharging(final boolean requiresCharging) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setRequiresDeviceIdle(final boolean requiresDeviceIdle) {
        throw new RuntimeException("Stub!");
    }
    
    public Request setVisibleInDownloadsUi(final boolean isVisible) {
        throw new RuntimeException("Stub!");
    }
}
