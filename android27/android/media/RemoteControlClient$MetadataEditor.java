package android.media;

import android.graphics.*;

@Deprecated
public class MetadataEditor extends MediaMetadataEditor
{
    public static final int BITMAP_KEY_ARTWORK = 100;
    
    MetadataEditor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized MetadataEditor putString(final int key, final String value) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized MetadataEditor putLong(final int key, final long value) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized MetadataEditor putBitmap(final int key, final Bitmap bitmap) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized MetadataEditor putObject(final int key, final Object object) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void clear() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void apply() {
        throw new RuntimeException("Stub!");
    }
}
