package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;
import java.io.*;
import android.graphics.*;

public final class MediaStore
{
    public static final String ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE";
    public static final String ACTION_IMAGE_CAPTURE_SECURE = "android.media.action.IMAGE_CAPTURE_SECURE";
    public static final String ACTION_VIDEO_CAPTURE = "android.media.action.VIDEO_CAPTURE";
    public static final String AUTHORITY = "media";
    public static final String EXTRA_DURATION_LIMIT = "android.intent.extra.durationLimit";
    public static final String EXTRA_FINISH_ON_COMPLETION = "android.intent.extra.finishOnCompletion";
    public static final String EXTRA_FULL_SCREEN = "android.intent.extra.fullScreen";
    public static final String EXTRA_MEDIA_ALBUM = "android.intent.extra.album";
    public static final String EXTRA_MEDIA_ARTIST = "android.intent.extra.artist";
    public static final String EXTRA_MEDIA_FOCUS = "android.intent.extra.focus";
    public static final String EXTRA_MEDIA_GENRE = "android.intent.extra.genre";
    public static final String EXTRA_MEDIA_PLAYLIST = "android.intent.extra.playlist";
    public static final String EXTRA_MEDIA_RADIO_CHANNEL = "android.intent.extra.radio_channel";
    public static final String EXTRA_MEDIA_TITLE = "android.intent.extra.title";
    public static final String EXTRA_OUTPUT = "output";
    public static final String EXTRA_SCREEN_ORIENTATION = "android.intent.extra.screenOrientation";
    public static final String EXTRA_SHOW_ACTION_ICONS = "android.intent.extra.showActionIcons";
    public static final String EXTRA_SIZE_LIMIT = "android.intent.extra.sizeLimit";
    public static final String EXTRA_VIDEO_QUALITY = "android.intent.extra.videoQuality";
    public static final String INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH = "android.media.action.MEDIA_PLAY_FROM_SEARCH";
    public static final String INTENT_ACTION_MEDIA_SEARCH = "android.intent.action.MEDIA_SEARCH";
    @Deprecated
    public static final String INTENT_ACTION_MUSIC_PLAYER = "android.intent.action.MUSIC_PLAYER";
    public static final String INTENT_ACTION_STILL_IMAGE_CAMERA = "android.media.action.STILL_IMAGE_CAMERA";
    public static final String INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE = "android.media.action.STILL_IMAGE_CAMERA_SECURE";
    public static final String INTENT_ACTION_TEXT_OPEN_FROM_SEARCH = "android.media.action.TEXT_OPEN_FROM_SEARCH";
    public static final String INTENT_ACTION_VIDEO_CAMERA = "android.media.action.VIDEO_CAMERA";
    public static final String INTENT_ACTION_VIDEO_PLAY_FROM_SEARCH = "android.media.action.VIDEO_PLAY_FROM_SEARCH";
    public static final String MEDIA_IGNORE_FILENAME = ".nomedia";
    public static final String MEDIA_SCANNER_VOLUME = "volume";
    public static final String META_DATA_STILL_IMAGE_CAMERA_PREWARM_SERVICE = "android.media.still_image_camera_preview_service";
    public static final String UNKNOWN_STRING = "<unknown>";
    
    public MediaStore() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getMediaScannerUri() {
        throw new RuntimeException("Stub!");
    }
    
    public static String getVersion(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getDocumentUri(final Context context, final Uri mediaUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Files
    {
        public Files() {
            throw new RuntimeException("Stub!");
        }
        
        public static Uri getContentUri(final String volumeName) {
            throw new RuntimeException("Stub!");
        }
        
        public static final Uri getContentUri(final String volumeName, final long rowId) {
            throw new RuntimeException("Stub!");
        }
        
        public interface FileColumns extends MediaColumns
        {
            public static final String MEDIA_TYPE = "media_type";
            public static final int MEDIA_TYPE_AUDIO = 2;
            public static final int MEDIA_TYPE_IMAGE = 1;
            public static final int MEDIA_TYPE_NONE = 0;
            public static final int MEDIA_TYPE_PLAYLIST = 4;
            public static final int MEDIA_TYPE_VIDEO = 3;
            public static final String MIME_TYPE = "mime_type";
            public static final String PARENT = "parent";
            public static final String TITLE = "title";
        }
    }
    
    public static final class Images
    {
        public Images() {
            throw new RuntimeException("Stub!");
        }
        
        public static final class Media implements ImageColumns
        {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/image";
            public static final String DEFAULT_SORT_ORDER = "bucket_display_name";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;
            
            public Media() {
                throw new RuntimeException("Stub!");
            }
            
            public static final Cursor query(final ContentResolver cr, final Uri uri, final String[] projection) {
                throw new RuntimeException("Stub!");
            }
            
            public static final Cursor query(final ContentResolver cr, final Uri uri, final String[] projection, final String where, final String orderBy) {
                throw new RuntimeException("Stub!");
            }
            
            public static final Cursor query(final ContentResolver cr, final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String orderBy) {
                throw new RuntimeException("Stub!");
            }
            
            public static final Bitmap getBitmap(final ContentResolver cr, final Uri url) throws FileNotFoundException, IOException {
                throw new RuntimeException("Stub!");
            }
            
            public static final String insertImage(final ContentResolver cr, final String imagePath, final String name, final String description) throws FileNotFoundException {
                throw new RuntimeException("Stub!");
            }
            
            public static final String insertImage(final ContentResolver cr, final Bitmap source, final String title, final String description) {
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
        
        public static class Thumbnails implements BaseColumns
        {
            public static final String DATA = "_data";
            public static final String DEFAULT_SORT_ORDER = "image_id ASC";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final int FULL_SCREEN_KIND = 2;
            public static final String HEIGHT = "height";
            public static final String IMAGE_ID = "image_id";
            public static final Uri INTERNAL_CONTENT_URI;
            public static final String KIND = "kind";
            public static final int MICRO_KIND = 3;
            public static final int MINI_KIND = 1;
            public static final String THUMB_DATA = "thumb_data";
            public static final String WIDTH = "width";
            
            public Thumbnails() {
                throw new RuntimeException("Stub!");
            }
            
            public static final Cursor query(final ContentResolver cr, final Uri uri, final String[] projection) {
                throw new RuntimeException("Stub!");
            }
            
            public static final Cursor queryMiniThumbnails(final ContentResolver cr, final Uri uri, final int kind, final String[] projection) {
                throw new RuntimeException("Stub!");
            }
            
            public static final Cursor queryMiniThumbnail(final ContentResolver cr, final long origId, final int kind, final String[] projection) {
                throw new RuntimeException("Stub!");
            }
            
            public static void cancelThumbnailRequest(final ContentResolver cr, final long origId) {
                throw new RuntimeException("Stub!");
            }
            
            public static Bitmap getThumbnail(final ContentResolver cr, final long origId, final int kind, final BitmapFactory.Options options) {
                throw new RuntimeException("Stub!");
            }
            
            public static void cancelThumbnailRequest(final ContentResolver cr, final long origId, final long groupId) {
                throw new RuntimeException("Stub!");
            }
            
            public static Bitmap getThumbnail(final ContentResolver cr, final long origId, final long groupId, final int kind, final BitmapFactory.Options options) {
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
        
        public interface ImageColumns extends MediaColumns
        {
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
            public static final String BUCKET_ID = "bucket_id";
            public static final String DATE_TAKEN = "datetaken";
            public static final String DESCRIPTION = "description";
            public static final String IS_PRIVATE = "isprivate";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";
            public static final String ORIENTATION = "orientation";
            public static final String PICASA_ID = "picasa_id";
        }
    }
    
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
    
    public static final class Video
    {
        public static final String DEFAULT_SORT_ORDER = "_display_name";
        
        public Video() {
            throw new RuntimeException("Stub!");
        }
        
        public static final Cursor query(final ContentResolver cr, final Uri uri, final String[] projection) {
            throw new RuntimeException("Stub!");
        }
        
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
        
        public static class Thumbnails implements BaseColumns
        {
            public static final String DATA = "_data";
            public static final String DEFAULT_SORT_ORDER = "video_id ASC";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final int FULL_SCREEN_KIND = 2;
            public static final String HEIGHT = "height";
            public static final Uri INTERNAL_CONTENT_URI;
            public static final String KIND = "kind";
            public static final int MICRO_KIND = 3;
            public static final int MINI_KIND = 1;
            public static final String VIDEO_ID = "video_id";
            public static final String WIDTH = "width";
            
            public Thumbnails() {
                throw new RuntimeException("Stub!");
            }
            
            public static void cancelThumbnailRequest(final ContentResolver cr, final long origId) {
                throw new RuntimeException("Stub!");
            }
            
            public static Bitmap getThumbnail(final ContentResolver cr, final long origId, final int kind, final BitmapFactory.Options options) {
                throw new RuntimeException("Stub!");
            }
            
            public static Bitmap getThumbnail(final ContentResolver cr, final long origId, final long groupId, final int kind, final BitmapFactory.Options options) {
                throw new RuntimeException("Stub!");
            }
            
            public static void cancelThumbnailRequest(final ContentResolver cr, final long origId, final long groupId) {
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
        
        public interface VideoColumns extends MediaColumns
        {
            public static final String ALBUM = "album";
            public static final String ARTIST = "artist";
            public static final String BOOKMARK = "bookmark";
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
            public static final String BUCKET_ID = "bucket_id";
            public static final String CATEGORY = "category";
            public static final String DATE_TAKEN = "datetaken";
            public static final String DESCRIPTION = "description";
            public static final String DURATION = "duration";
            public static final String IS_PRIVATE = "isprivate";
            public static final String LANGUAGE = "language";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";
            public static final String RESOLUTION = "resolution";
            public static final String TAGS = "tags";
        }
    }
    
    public interface MediaColumns extends BaseColumns
    {
        public static final String DATA = "_data";
        public static final String DATE_ADDED = "date_added";
        public static final String DATE_MODIFIED = "date_modified";
        public static final String DISPLAY_NAME = "_display_name";
        public static final String HEIGHT = "height";
        public static final String MIME_TYPE = "mime_type";
        public static final String SIZE = "_size";
        public static final String TITLE = "title";
        public static final String WIDTH = "width";
    }
}
