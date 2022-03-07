package android.provider;

import android.net.*;

public static final class Media implements VideoColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/video";
    public static final String DEFAULT_SORT_ORDER = "title";
    public static final Uri EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI;
    
    public Media() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUri(final String volumeName) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        EXTERNAL_CONTENT_URI = null;
        INTERNAL_CONTENT_URI = null;
    }
}
