package android.media;

import android.util.*;

public static final class AudioCapabilities
{
    AudioCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getBitrateRange() {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getSupportedSampleRates() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer>[] getSupportedSampleRateRanges() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxInputChannelCount() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSampleRateSupported(final int sampleRate) {
        throw new RuntimeException("Stub!");
    }
}
