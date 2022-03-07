package android.provider;

import android.net.*;

public static class StatusUpdates implements StatusColumns, PresenceColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/status-update";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/status-update";
    public static final Uri CONTENT_URI;
    public static final Uri PROFILE_CONTENT_URI;
    
    StatusUpdates() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getPresenceIconResourceId(final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getPresencePrecedence(final int status) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        PROFILE_CONTENT_URI = null;
    }
}
