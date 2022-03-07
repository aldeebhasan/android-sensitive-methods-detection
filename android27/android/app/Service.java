package android.app;

import android.content.*;
import android.content.res.*;
import android.os.*;
import java.io.*;

public abstract class Service extends ContextWrapper implements ComponentCallbacks2
{
    public static final int START_CONTINUATION_MASK = 15;
    public static final int START_FLAG_REDELIVERY = 1;
    public static final int START_FLAG_RETRY = 2;
    public static final int START_NOT_STICKY = 2;
    public static final int START_REDELIVER_INTENT = 3;
    public static final int START_STICKY = 1;
    public static final int START_STICKY_COMPATIBILITY = 0;
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int STOP_FOREGROUND_REMOVE = 1;
    
    public Service() {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public final Application getApplication() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onStart(final Intent intent, final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onLowMemory() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTrimMemory(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract IBinder onBind(final Intent p0);
    
    public boolean onUnbind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRebind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTaskRemoved(final Intent rootIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public final void stopSelf() {
        throw new RuntimeException("Stub!");
    }
    
    public final void stopSelf(final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean stopSelfResult(final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    public final void startForeground(final int id, final Notification notification) {
        throw new RuntimeException("Stub!");
    }
    
    public final void stopForeground(final boolean removeNotification) {
        throw new RuntimeException("Stub!");
    }
    
    public final void stopForeground(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    protected void dump(final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
