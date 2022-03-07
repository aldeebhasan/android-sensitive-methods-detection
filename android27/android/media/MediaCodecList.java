package android.media;

public final class MediaCodecList
{
    public static final int ALL_CODECS = 1;
    public static final int REGULAR_CODECS = 0;
    
    public MediaCodecList(final int kind) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final int getCodecCount() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final MediaCodecInfo getCodecInfoAt(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public final MediaCodecInfo[] getCodecInfos() {
        throw new RuntimeException("Stub!");
    }
    
    public final String findDecoderForFormat(final MediaFormat format) {
        throw new RuntimeException("Stub!");
    }
    
    public final String findEncoderForFormat(final MediaFormat format) {
        throw new RuntimeException("Stub!");
    }
}
