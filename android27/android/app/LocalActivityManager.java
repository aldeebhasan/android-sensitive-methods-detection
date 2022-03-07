package android.app;

import android.content.*;
import android.view.*;
import android.os.*;

@Deprecated
public class LocalActivityManager
{
    public LocalActivityManager(final Activity parent, final boolean singleMode) {
        throw new RuntimeException("Stub!");
    }
    
    public Window startActivity(final String id, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Window destroyActivity(final String id, final boolean finish) {
        throw new RuntimeException("Stub!");
    }
    
    public Activity getCurrentActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCurrentId() {
        throw new RuntimeException("Stub!");
    }
    
    public Activity getActivity(final String id) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchCreate(final Bundle state) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle saveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchResume() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchPause(final boolean finishing) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchStop() {
        throw new RuntimeException("Stub!");
    }
    
    public void removeAllActivities() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchDestroy(final boolean finishing) {
        throw new RuntimeException("Stub!");
    }
}
