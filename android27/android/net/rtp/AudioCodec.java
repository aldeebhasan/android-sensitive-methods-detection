package android.net.rtp;

public class AudioCodec
{
    public static final AudioCodec AMR;
    public static final AudioCodec GSM;
    public static final AudioCodec GSM_EFR;
    public static final AudioCodec PCMA;
    public static final AudioCodec PCMU;
    public final String fmtp;
    public final String rtpmap;
    public final int type;
    
    AudioCodec() {
        throw new RuntimeException("Stub!");
    }
    
    public static AudioCodec[] getCodecs() {
        throw new RuntimeException("Stub!");
    }
    
    public static AudioCodec getCodec(final int type, final String rtpmap, final String fmtp) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        AMR = null;
        GSM = null;
        GSM_EFR = null;
        PCMA = null;
        PCMU = null;
    }
}
