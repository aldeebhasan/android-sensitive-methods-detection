package android.provider;

import android.net.*;

public static final class Albums implements BaseColumns, AlbumColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/albums";
    public static final String DEFAULT_SORT_ORDER = "album_key";
    public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/album";
    public static final Uri EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI;
    
    public Albums() {
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
