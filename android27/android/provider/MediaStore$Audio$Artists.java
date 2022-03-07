package android.provider;

import android.net.*;

public static final class Artists implements BaseColumns, ArtistColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/artists";
    public static final String DEFAULT_SORT_ORDER = "artist_key";
    public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/artist";
    public static final Uri EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI;
    
    public Artists() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUri(final String volumeName) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        EXTERNAL_CONTENT_URI = null;
        INTERNAL_CONTENT_URI = null;
    }
    
    public static final class Albums implements AlbumColumns
    {
        public Albums() {
            throw new RuntimeException("Stub!");
        }
        
        public static final Uri getContentUri(final String volumeName, final long artistId) {
            throw new RuntimeException("Stub!");
        }
    }
}
