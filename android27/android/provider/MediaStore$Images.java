package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;
import java.io.*;
import android.graphics.*;

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
