package android.provider;

import android.net.*;
import android.content.*;

public static final class Playlists implements BaseColumns, PlaylistsColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/playlist";
    public static final String DEFAULT_SORT_ORDER = "name";
    public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/playlist";
    public static final Uri EXTERNAL_CONTENT_URI;
    public static final Uri INTERNAL_CONTENT_URI;
    
    public Playlists() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContentUri(final String volumeName) {
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
}
