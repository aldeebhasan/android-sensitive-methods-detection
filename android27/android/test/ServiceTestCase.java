package android.test;

import android.os.*;
import android.app.*;
import android.content.*;

@Deprecated
public abstract class ServiceTestCase<T extends Service> extends AndroidTestCase
{
    public ServiceTestCase(final Class<T> serviceClass) {
        throw new RuntimeException("Stub!");
    }
    
    public T getService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    protected void setupService() {
        throw new RuntimeException("Stub!");
    }
    
    protected void startService(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    protected IBinder bindService(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    protected void shutdownService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    public void setApplication(final Application application) {
        throw new RuntimeException("Stub!");
    }
    
    public Application getApplication() {
        throw new RuntimeException("Stub!");
    }
    
    public Context getSystemContext() {
        throw new RuntimeException("Stub!");
    }
    
    public void testServiceTestCaseSetUpProperly() throws Exception {
        throw new RuntimeException("Stub!");
    }
}
