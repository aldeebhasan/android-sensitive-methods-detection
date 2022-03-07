package android.media.tv;

public abstract static class TimeShiftPositionCallback
{
    public TimeShiftPositionCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftStartPositionChanged(final String inputId, final long timeMs) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftCurrentPositionChanged(final String inputId, final long timeMs) {
        throw new RuntimeException("Stub!");
    }
}
