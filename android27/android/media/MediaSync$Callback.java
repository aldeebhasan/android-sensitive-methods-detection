package android.media;

import java.nio.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onAudioBufferConsumed(final MediaSync p0, final ByteBuffer p1, final int p2);
}
