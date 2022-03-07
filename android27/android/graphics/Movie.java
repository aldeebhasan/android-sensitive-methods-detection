package android.graphics;

import java.io.*;

public class Movie
{
    Movie() {
        throw new RuntimeException("Stub!");
    }
    
    public native int width();
    
    public native int height();
    
    public native boolean isOpaque();
    
    public native int duration();
    
    public native boolean setTime(final int p0);
    
    public void draw(final Canvas canvas, final float x, final float y, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void draw(final Canvas canvas, final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    public static Movie decodeStream(final InputStream is) {
        throw new RuntimeException("Stub!");
    }
    
    public static native Movie decodeByteArray(final byte[] p0, final int p1, final int p2);
    
    public static Movie decodeFile(final String pathName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
