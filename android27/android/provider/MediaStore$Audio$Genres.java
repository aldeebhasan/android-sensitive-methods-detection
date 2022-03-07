package android.provider;

import android.net.*;

public static final class Genres implements BaseColumns, GenresColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/genre";
    public static final String DEFAULT_SORT_ORDER = "name";
    public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/genre";
    public static final Uri EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI;
    
    public Genres() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUri(final String volumeName) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUriForAudioId(final String volumeName, final int audioId) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        EXTERNAL_CONTENT_URI = null;
        INTERNAL_CONTENT_URI = null;
    }
    
    public static final class Members implements AudioColumns
    {
        public static final String AUDIO_ID = "audio_id";
        public static final String CONTENT_DIRECTORY = "members";
        public static final String DEFAULT_SORT_ORDER = "title_key";
        public static final String GENRE_ID = "genre_id";
        
        public Members() {
            throw new RuntimeException("Stub!");
        }
        
        public static final Uri getContentUri(final String volumeName, final long genreId) {
            throw new RuntimeException("Stub!");
        }
    }
}
