package android.test;

import android.app.*;

@Deprecated
public abstract class ActivityInstrumentationTestCase<T extends Activity> extends ActivityTestCase
{
    public ActivityInstrumentationTestCase(final String pkg, final Class<T> activityClass) {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityInstrumentationTestCase(final String pkg, final Class<T> activityClass, final boolean initialTouchMode) {
        throw new RuntimeException("Stub!");
    }
    
    public T getActivity() {
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
    
    public void testActivityTestCaseSetUpProperly() throws Exception {
        throw new RuntimeException("Stub!");
    }
}
