package android.media;

import android.graphics.*;
import java.nio.*;

public abstract class Image implements AutoCloseable
{
    Image() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getFormat();
    
    public abstract int getWidth();
    
    public abstract int getHeight();
    
    public abstract long getTimestamp();
    
    public void setTimestamp(final long timestamp) {
        throw new RuntimeException("Stub!");
    }
    
    public Rect getCropRect() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCropRect(final Rect cropRect) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Plane[] getPlanes();
    
    @Override
    public abstract void close();
    
    public abstract static class Plane
    {
        Plane() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract int getRowStride();
        
        public abstract int getPixelStride();
        
        public abstract ByteBuffer getBuffer();
    }
}
