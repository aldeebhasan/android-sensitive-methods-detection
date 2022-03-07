package android.media;

import android.util.*;

public static final class EncoderCapabilities
{
    public static final int BITRATE_MODE_CBR = 2;
    public static final int BITRATE_MODE_CQ = 0;
    public static final int BITRATE_MODE_VBR = 1;
    
    EncoderCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getComplexityRange() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isBitrateModeSupported(final int mode) {
        throw new RuntimeException("Stub!");
    }
}
