package android.test;

import android.app.*;
import android.content.*;

@Deprecated
public abstract class ActivityInstrumentationTestCase2<T extends Activity> extends ActivityTestCase
{
    public ActivityInstrumentationTestCase2(final String pkg, final Class<T> activityClass) {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityInstrumentationTestCase2(final Class<T> activityClass) {
        throw new RuntimeException("Stub!");
    }
    
    public T getActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setActivityIntent(final Intent i) {
        throw new RuntimeException("Stub!");
    }
    
    public void setActivityInitialTouchMode(final boolean initialTouchMode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void runTest() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
