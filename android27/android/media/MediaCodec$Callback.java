package android.media;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onInputBufferAvailable(final MediaCodec p0, final int p1);
    
    public abstract void onOutputBufferAvailable(final MediaCodec p0, final int p1, final BufferInfo p2);
    
    public abstract void onError(final MediaCodec p0, final CodecException p1);
    
    public abstract void onOutputFormatChanged(final MediaCodec p0, final MediaFormat p1);
}
