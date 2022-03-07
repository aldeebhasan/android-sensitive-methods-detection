package android.os;

public final class SystemClock
{
    SystemClock() {
        throw new RuntimeException("Stub!");
    }
    
    public static void sleep(final long ms) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean setCurrentTimeMillis(final long millis) {
        throw new RuntimeException("Stub!");
    }
    
    public static native long uptimeMillis();
    
    public static native long elapsedRealtime();
    
    public static native long elapsedRealtimeNanos();
    
    public static native long currentThreadTimeMillis();
}
