package android.test;

import android.app.*;
import android.content.*;

@Deprecated
public abstract class ApplicationTestCase<T extends Application> extends AndroidTestCase
{
    public ApplicationTestCase(final Class<T> applicationClass) {
        throw new RuntimeException("Stub!");
    }
    
    public T getApplication() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    protected final void createApplication() {
        throw new RuntimeException("Stub!");
    }
    
    protected final void terminateApplication() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    public Context getSystemContext() {
        throw new RuntimeException("Stub!");
    }
    
    public final void testApplicationTestCaseSetUpProperly() throws Exception {
        throw new RuntimeException("Stub!");
    }
}
