package android.provider;

import android.net.*;
import android.content.*;

public static final class Audio
{
    public Audio() {
        throw new RuntimeException("Stub!");
    }
    
    public static String keyFor(final String name) {
        throw new RuntimeException("Stub!");
    }
    
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
    
    public static final class Radio
    {
        public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/radio";
        
        Radio() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface AlbumColumns
    {
        public static final String ALBUM = "album";
        public static final String ALBUM_ART = "album_art";
        public static final String ALBUM_ID = "album_id";
        public static final String ALBUM_KEY = "album_key";
        public static final String ARTIST = "artist";
        public static final String FIRST_YEAR = "minyear";
        public static final String LAST_YEAR = "maxyear";
        public static final String NUMBER_OF_SONGS = "numsongs";
        public static final String NUMBER_OF_SONGS_FOR_ARTIST = "numsongs_by_artist";
    }
    
    public interface ArtistColumns
    {
        public static final String ARTIST = "artist";
        public static final String ARTIST_KEY = "artist_key";
        public static final String NUMBER_OF_ALBUMS = "number_of_albums";
        public static final String NUMBER_OF_TRACKS = "number_of_tracks";
    }
    
    public interface AudioColumns extends MediaColumns
    {
        public static final String ALBUM = "album";
        public static final String ALBUM_ID = "album_id";
        public static final String ALBUM_KEY = "album_key";
        public static final String ARTIST = "artist";
        public static final String ARTIST_ID = "artist_id";
        public static final String ARTIST_KEY = "artist_key";
        public static final String BOOKMARK = "bookmark";
        public static final String COMPOSER = "composer";
        public static final String DURATION = "duration";
        public static final String IS_ALARM = "is_alarm";
        public static final String IS_MUSIC = "is_music";
        public static final String IS_NOTIFICATION = "is_notification";
        public static final String IS_PODCAST = "is_podcast";
        public static final String IS_RINGTONE = "is_ringtone";
        public static final String TITLE_KEY = "title_key";
        public static final String TRACK = "track";
        public static final String YEAR = "year";
    }
    
    public interface PlaylistsColumns
    {
        public static final String DATA = "_data";
        public static final String DATE_ADDED = "date_added";
        public static final String DATE_MODIFIED = "date_modified";
        public static final String NAME = "name";
    }
    
    public interface GenresColumns
    {
        public static final String NAME = "name";
    }
}
