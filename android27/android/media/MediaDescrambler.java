package android.media;

import java.nio.*;

public final class MediaDescrambler implements AutoCloseable
{
    public MediaDescrambler(final int CA_system_id) throws MediaCasException.UnsupportedCasException {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean requiresSecureDecoderComponent(final String mime) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setMediaCasSession(final MediaCas.Session session) {
        throw new RuntimeException("Stub!");
    }
    
    public final int descramble(final ByteBuffer srcBuf, final ByteBuffer dstBuf, final MediaCodec.CryptoInfo cryptoInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
}
