package android.test;

import android.app.*;

@Deprecated
public abstract class SingleLaunchActivityTestCase<T extends Activity> extends InstrumentationTestCase
{
    public SingleLaunchActivityTestCase(final String pkg, final Class<T> activityClass) {
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
