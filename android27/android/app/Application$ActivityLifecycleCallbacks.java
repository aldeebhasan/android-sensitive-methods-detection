package android.app;

import android.os.*;

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
