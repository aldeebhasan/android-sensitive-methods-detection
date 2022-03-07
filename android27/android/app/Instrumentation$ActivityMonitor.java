package android.app;

import android.content.*;

public static class ActivityMonitor
{
    public ActivityMonitor(final IntentFilter which, final ActivityResult result, final boolean block) {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityMonitor(final String cls, final ActivityResult result, final boolean block) {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityMonitor() {
        throw new RuntimeException("Stub!");
    }
    
    public final IntentFilter getFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public final ActivityResult getResult() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isBlocking() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getHits() {
        throw new RuntimeException("Stub!");
    }
    
    public final Activity getLastActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public final Activity waitForActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public final Activity waitForActivityWithTimeout(final long timeOut) {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityResult onStartActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
