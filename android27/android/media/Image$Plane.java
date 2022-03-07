package android.media;

import java.nio.*;

public abstract static class Plane
{
    Plane() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getRowStride();
    
    public abstract int getPixelStride();
    
    public abstract ByteBuffer getBuffer();
}
