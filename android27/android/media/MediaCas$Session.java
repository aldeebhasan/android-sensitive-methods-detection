package android.media;

public final class Session implements AutoCloseable
{
    Session() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPrivateData(final byte[] data) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void processEcm(final byte[] data, final int offset, final int length) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void processEcm(final byte[] data) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
}
