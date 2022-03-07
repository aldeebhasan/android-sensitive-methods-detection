package android.media;

public static final class CodecCapabilities
{
    @Deprecated
    public static final int COLOR_Format12bitRGB444 = 3;
    @Deprecated
    public static final int COLOR_Format16bitARGB1555 = 5;
    @Deprecated
    public static final int COLOR_Format16bitARGB4444 = 4;
    @Deprecated
    public static final int COLOR_Format16bitBGR565 = 7;
    public static final int COLOR_Format16bitRGB565 = 6;
    @Deprecated
    public static final int COLOR_Format18BitBGR666 = 41;
    @Deprecated
    public static final int COLOR_Format18bitARGB1665 = 9;
    @Deprecated
    public static final int COLOR_Format18bitRGB666 = 8;
    @Deprecated
    public static final int COLOR_Format19bitARGB1666 = 10;
    @Deprecated
    public static final int COLOR_Format24BitABGR6666 = 43;
    @Deprecated
    public static final int COLOR_Format24BitARGB6666 = 42;
    @Deprecated
    public static final int COLOR_Format24bitARGB1887 = 13;
    public static final int COLOR_Format24bitBGR888 = 12;
    @Deprecated
    public static final int COLOR_Format24bitRGB888 = 11;
    @Deprecated
    public static final int COLOR_Format25bitARGB1888 = 14;
    public static final int COLOR_Format32bitABGR8888 = 2130747392;
    @Deprecated
    public static final int COLOR_Format32bitARGB8888 = 16;
    @Deprecated
    public static final int COLOR_Format32bitBGRA8888 = 15;
    @Deprecated
    public static final int COLOR_Format8bitRGB332 = 2;
    @Deprecated
    public static final int COLOR_FormatCbYCrY = 27;
    @Deprecated
    public static final int COLOR_FormatCrYCbY = 28;
    public static final int COLOR_FormatL16 = 36;
    @Deprecated
    public static final int COLOR_FormatL2 = 33;
    @Deprecated
    public static final int COLOR_FormatL24 = 37;
    @Deprecated
    public static final int COLOR_FormatL32 = 38;
    @Deprecated
    public static final int COLOR_FormatL4 = 34;
    public static final int COLOR_FormatL8 = 35;
    @Deprecated
    public static final int COLOR_FormatMonochrome = 1;
    public static final int COLOR_FormatRGBAFlexible = 2134288520;
    public static final int COLOR_FormatRGBFlexible = 2134292616;
    public static final int COLOR_FormatRawBayer10bit = 31;
    public static final int COLOR_FormatRawBayer8bit = 30;
    public static final int COLOR_FormatRawBayer8bitcompressed = 32;
    public static final int COLOR_FormatSurface = 2130708361;
    @Deprecated
    public static final int COLOR_FormatYCbYCr = 25;
    @Deprecated
    public static final int COLOR_FormatYCrYCb = 26;
    @Deprecated
    public static final int COLOR_FormatYUV411PackedPlanar = 18;
    @Deprecated
    public static final int COLOR_FormatYUV411Planar = 17;
    public static final int COLOR_FormatYUV420Flexible = 2135033992;
    @Deprecated
    public static final int COLOR_FormatYUV420PackedPlanar = 20;
    @Deprecated
    public static final int COLOR_FormatYUV420PackedSemiPlanar = 39;
    @Deprecated
    public static final int COLOR_FormatYUV420Planar = 19;
    @Deprecated
    public static final int COLOR_FormatYUV420SemiPlanar = 21;
    public static final int COLOR_FormatYUV422Flexible = 2135042184;
    @Deprecated
    public static final int COLOR_FormatYUV422PackedPlanar = 23;
    @Deprecated
    public static final int COLOR_FormatYUV422PackedSemiPlanar = 40;
    @Deprecated
    public static final int COLOR_FormatYUV422Planar = 22;
    @Deprecated
    public static final int COLOR_FormatYUV422SemiPlanar = 24;
    public static final int COLOR_FormatYUV444Flexible = 2135181448;
    @Deprecated
    public static final int COLOR_FormatYUV444Interleaved = 29;
    @Deprecated
    public static final int COLOR_QCOM_FormatYUV420SemiPlanar = 2141391872;
    @Deprecated
    public static final int COLOR_TI_FormatYUV420PackedSemiPlanar = 2130706688;
    public static final String FEATURE_AdaptivePlayback = "adaptive-playback";
    public static final String FEATURE_IntraRefresh = "intra-refresh";
    public static final String FEATURE_PartialFrame = "partial-frame";
    public static final String FEATURE_SecurePlayback = "secure-playback";
    public static final String FEATURE_TunneledPlayback = "tunneled-playback";
    public int[] colorFormats;
    public CodecProfileLevel[] profileLevels;
    
    public CodecCapabilities() {
        this.colorFormats = null;
        this.profileLevels = null;
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isFeatureSupported(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isFeatureRequired(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isFormatSupported(final MediaFormat format) {
        throw new RuntimeException("Stub!");
    }
    
    public MediaFormat getDefaultFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMimeType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxSupportedInstances() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioCapabilities getAudioCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public EncoderCapabilities getEncoderCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public VideoCapabilities getVideoCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public static CodecCapabilities createFromProfileLevel(final String mime, final int profile, final int level) {
        throw new RuntimeException("Stub!");
    }
}
