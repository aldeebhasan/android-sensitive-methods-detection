package android.media;

import android.graphics.*;

@Deprecated
public abstract class MediaMetadataEditor
{
    public static final int BITMAP_KEY_ARTWORK = 100;
    public static final int RATING_KEY_BY_OTHERS = 101;
    public static final int RATING_KEY_BY_USER = 268435457;
    
    MediaMetadataEditor() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void apply();
    
    public synchronized void clear() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void addEditableKey(final int key) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void removeEditableKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized int[] getEditableKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized MediaMetadataEditor putString(final int key, final String value) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized MediaMetadataEditor putLong(final int key, final long value) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized MediaMetadataEditor putBitmap(final int key, final Bitmap bitmap) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized MediaMetadataEditor putObject(final int key, final Object value) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized long getLong(final int key, final long defaultValue) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized String getString(final int key, final String defaultValue) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized Bitmap getBitmap(final int key, final Bitmap defaultValue) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized Object getObject(final int key, final Object defaultValue) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
}
