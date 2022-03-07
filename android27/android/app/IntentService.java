package android.app;

import android.content.*;
import android.os.*;

public abstract class IntentService extends Service
{
    public IntentService(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntentRedelivery(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStart(final Intent intent, final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    protected abstract void onHandleIntent(final Intent p0);
}
