package android.test;

import android.os.*;
import android.app.*;
import android.content.*;

@Deprecated
public abstract class ActivityUnitTestCase<T extends Activity> extends ActivityTestCase
{
    public ActivityUnitTestCase(final Class<T> activityClass) {
        throw new RuntimeException("Stub!");
    }
    
    public T getActivity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    protected T startActivity(final Intent intent, final Bundle savedInstanceState, final Object lastNonConfigurationInstance) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    public void setApplication(final Application application) {
        throw new RuntimeException("Stub!");
    }
    
    public void setActivityContext(final Context activityContext) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRequestedOrientation() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getStartedActivityIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public int getStartedActivityRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFinishCalled() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFinishedActivityRequest() {
        throw new RuntimeException("Stub!");
    }
}
