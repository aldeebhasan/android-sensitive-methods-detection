package android.provider;

import android.net.*;

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
