package android.media;

import java.util.*;

public final class MediaCrypto
{
    public MediaCrypto(final UUID uuid, final byte[] initData) throws MediaCryptoException {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isCryptoSchemeSupported(final UUID uuid) {
        throw new RuntimeException("Stub!");
    }
    
    public final native boolean requiresSecureDecoderComponent(final String p0);
    
    public final native void setMediaDrmSession(final byte[] p0) throws MediaCryptoException;
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
    
    public final native void release();
}
