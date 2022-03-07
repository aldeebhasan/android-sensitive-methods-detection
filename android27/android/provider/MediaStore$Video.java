package android.provider;

import android.content.*;
import android.net.*;
import android.database.*;
import android.graphics.*;

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
