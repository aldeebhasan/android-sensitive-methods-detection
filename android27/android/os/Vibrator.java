package android.os;

import android.media.*;

public abstract class Vibrator
{
    Vibrator() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean hasVibrator();
    
    public abstract boolean hasAmplitudeControl();
    
    @Deprecated
    public void vibrate(final long milliseconds) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void vibrate(final long milliseconds, final AudioAttributes attributes) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void vibrate(final long[] pattern, final int repeat) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void vibrate(final long[] pattern, final int repeat, final AudioAttributes attributes) {
        throw new RuntimeException("Stub!");
    }
    
    public void vibrate(final VibrationEffect vibe) {
        throw new RuntimeException("Stub!");
    }
    
    public void vibrate(final VibrationEffect vibe, final AudioAttributes attributes) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void cancel();
}
