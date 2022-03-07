package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;
import android.graphics.*;

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
