package android.provider;

import android.net.*;

public static final class Media implements AudioColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/audio";
    public static final String DEFAULT_SORT_ORDER = "title_key";
    public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/audio";
    public static final Uri EXTERNAL_CONTENT_URI;
    public static final String EXTRA_MAX_BYTES = "android.provider.MediaStore.extra.MAX_BYTES";
    public static final Uri INTERNAL_CONTENT_URI;
    public static final String RECORD_SOUND_ACTION = "android.provider.MediaStore.RECORD_SOUND";
    
    public Media() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUri(final String volumeName) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUriForPath(final String path) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        EXTERNAL_CONTENT_URI = null;
        INTERNAL_CONTENT_URI = null;
    }
}
