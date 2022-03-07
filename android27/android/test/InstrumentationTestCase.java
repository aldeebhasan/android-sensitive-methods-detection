package android.test;

import junit.framework.*;
import android.os.*;
import android.app.*;
import android.content.*;

@Deprecated
public class InstrumentationTestCase extends TestCase
{
    public InstrumentationTestCase() {
        throw new RuntimeException("Stub!");
    }
    
    public void injectInstrumentation(final Instrumentation instrumentation) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void injectInsrumentation(final Instrumentation instrumentation) {
        throw new RuntimeException("Stub!");
    }
    
    public Instrumentation getInstrumentation() {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Activity> T launchActivity(final String pkg, final Class<T> activityCls, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Activity> T launchActivityWithIntent(final String pkg, final Class<T> activityCls, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void runTestOnUiThread(final Runnable r) throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void runTest() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public void sendKeys(final String keysSequence) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendKeys(final int... keys) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendRepeatedKeys(final int... keys) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
}
