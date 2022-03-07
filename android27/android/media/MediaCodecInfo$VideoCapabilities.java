package android.media;

import android.util.*;

public static final class VideoCapabilities
{
    VideoCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getBitrateRange() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getSupportedWidths() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getSupportedHeights() {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidthAlignment() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeightAlignment() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getSupportedFrameRates() {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getSupportedWidthsFor(final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Integer> getSupportedHeightsFor(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Double> getSupportedFrameRatesFor(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Range<Double> getAchievableFrameRatesFor(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean areSizeAndRateSupported(final int width, final int height, final double frameRate) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSizeSupported(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
}
