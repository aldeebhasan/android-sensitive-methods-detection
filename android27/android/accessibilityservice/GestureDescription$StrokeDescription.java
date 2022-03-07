package android.accessibilityservice;

import android.graphics.*;

public static class StrokeDescription
{
    public StrokeDescription(final Path path, final long startTime, final long duration) {
        throw new RuntimeException("Stub!");
    }
    
    public StrokeDescription(final Path path, final long startTime, final long duration, final boolean willContinue) {
        throw new RuntimeException("Stub!");
    }
    
    public Path getPath() {
        throw new RuntimeException("Stub!");
    }
    
    public long getStartTime() {
        throw new RuntimeException("Stub!");
    }
    
    public long getDuration() {
        throw new RuntimeException("Stub!");
    }
    
    public StrokeDescription continueStroke(final Path path, final long startTime, final long duration, final boolean willContinue) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean willContinue() {
        throw new RuntimeException("Stub!");
    }
}
