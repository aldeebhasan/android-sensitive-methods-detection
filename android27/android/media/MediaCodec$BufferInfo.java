package android.media;

public static final class BufferInfo
{
    public int flags;
    public int offset;
    public long presentationTimeUs;
    public int size;
    
    public BufferInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int newOffset, final int newSize, final long newTimeUs, final int newFlags) {
        throw new RuntimeException("Stub!");
    }
}
