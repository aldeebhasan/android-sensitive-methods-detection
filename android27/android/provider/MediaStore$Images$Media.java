package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import java.io.*;

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
