package android.provider;

import android.net.*;
import android.content.*;

public static final class Members implements AudioColumns
{
    public static final String AUDIO_ID = "audio_id";
    public static final String CONTENT_DIRECTORY = "members";
    public static final String DEFAULT_SORT_ORDER = "play_order";
    public static final String PLAYLIST_ID = "playlist_id";
    public static final String PLAY_ORDER = "play_order";
    public static final String _ID = "_id";
    
    public Members() {
        throw new RuntimeException("Stub!");
    }
    
    public static final Uri getContentUri(final String volumeName, final long playlistId) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean moveItem(final ContentResolver res, final long playlistId, final int from, final int to) {
        throw new RuntimeException("Stub!");
    }
}
