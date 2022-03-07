package android.os;

public final class PowerManager
{
    public static final int ACQUIRE_CAUSES_WAKEUP = 268435456;
    public static final String ACTION_DEVICE_IDLE_MODE_CHANGED = "android.os.action.DEVICE_IDLE_MODE_CHANGED";
    public static final String ACTION_POWER_SAVE_MODE_CHANGED = "android.os.action.POWER_SAVE_MODE_CHANGED";
    @Deprecated
    public static final int FULL_WAKE_LOCK = 26;
    public static final int ON_AFTER_RELEASE = 536870912;
    public static final int PARTIAL_WAKE_LOCK = 1;
    public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
    public static final int RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY = 1;
    @Deprecated
    public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;
    @Deprecated
    public static final int SCREEN_DIM_WAKE_LOCK = 6;
    
    PowerManager() {
        throw new RuntimeException("Stub!");
    }
    
    public WakeLock newWakeLock(final int levelAndFlags, final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isWakeLockLevelSupported(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean isScreenOn() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInteractive() {
        throw new RuntimeException("Stub!");
    }
    
    public void reboot(final String reason) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPowerSaveMode() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDeviceIdleMode() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isIgnoringBatteryOptimizations(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSustainedPerformanceModeSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public final class WakeLock
    {
        WakeLock() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
        
        public void setReferenceCounted(final boolean value) {
            throw new RuntimeException("Stub!");
        }
        
        public void acquire() {
            throw new RuntimeException("Stub!");
        }
        
        public void acquire(final long timeout) {
            throw new RuntimeException("Stub!");
        }
        
        public void release() {
            throw new RuntimeException("Stub!");
        }
        
        public void release(final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isHeld() {
            throw new RuntimeException("Stub!");
        }
        
        public void setWorkSource(final WorkSource ws) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
}
