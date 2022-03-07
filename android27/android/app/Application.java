package android.app;

import android.content.res.*;
import android.content.*;
import android.os.*;

public class Application extends ContextWrapper implements ComponentCallbacks2
{
    public Application() {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTerminate() {
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
    
    @Override
    public void registerComponentCallbacks(final ComponentCallbacks callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void unregisterComponentCallbacks(final ComponentCallbacks callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerActivityLifecycleCallbacks(final ActivityLifecycleCallbacks callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterActivityLifecycleCallbacks(final ActivityLifecycleCallbacks callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerOnProvideAssistDataListener(final OnProvideAssistDataListener callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterOnProvideAssistDataListener(final OnProvideAssistDataListener callback) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnProvideAssistDataListener
    {
        void onProvideAssistData(final Activity p0, final Bundle p1);
    }
    
    public interface ActivityLifecycleCallbacks
    {
        void onActivityCreated(final Activity p0, final Bundle p1);
        
        void onActivityStarted(final Activity p0);
        
        void onActivityResumed(final Activity p0);
        
        void onActivityPaused(final Activity p0);
        
        void onActivityStopped(final Activity p0);
        
        void onActivitySaveInstanceState(final Activity p0, final Bundle p1);
        
        void onActivityDestroyed(final Activity p0);
    }
}
