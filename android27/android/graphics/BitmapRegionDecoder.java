package android.graphics;

import java.io.*;

public final class BitmapRegionDecoder
{
    BitmapRegionDecoder() {
        throw new RuntimeException("Stub!");
    }
    
    public static BitmapRegionDecoder newInstance(final byte[] data, final int offset, final int length, final boolean isShareable) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static BitmapRegionDecoder newInstance(final FileDescriptor fd, final boolean isShareable) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static BitmapRegionDecoder newInstance(final InputStream is, final boolean isShareable) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static BitmapRegionDecoder newInstance(final String pathName, final boolean isShareable) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap decodeRegion(final Rect rect, final BitmapFactory.Options options) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public void recycle() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isRecycled() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
