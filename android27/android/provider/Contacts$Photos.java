package android.provider;

import android.net.*;

@Deprecated
public static final class Photos implements BaseColumns, PhotosColumns
{
    @Deprecated
    public static final String CONTENT_DIRECTORY = "photo";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "person ASC";
    
    Photos() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
