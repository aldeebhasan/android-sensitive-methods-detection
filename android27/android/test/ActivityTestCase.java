package android.test;

import android.app.*;

@Deprecated
public abstract class ActivityTestCase extends InstrumentationTestCase
{
    public ActivityTestCase() {
        throw new RuntimeException("Stub!");
    }
    
    protected Activity getActivity() {
        throw new RuntimeException("Stub!");
    }
    
    protected void setActivity(final Activity testActivity) {
        throw new RuntimeException("Stub!");
    }
    
    protected void scrubClass(final Class<?> testCaseClass) throws IllegalAccessException {
        throw new RuntimeException("Stub!");
    }
}
