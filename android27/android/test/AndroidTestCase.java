package android.test;

import junit.framework.*;
import android.content.*;
import android.test.suitebuilder.annotation.*;
import android.net.*;

@Deprecated
public class AndroidTestCase extends TestCase
{
    protected Context mContext;
    
    public AndroidTestCase() {
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
    
    @Suppress
    public void testAndroidTestCaseSetupProperly() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContext(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public void assertActivityRequiresPermission(final String packageName, final String className, final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public void assertReadingContentUriRequiresPermission(final Uri uri, final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public void assertWritingContentUriRequiresPermission(final Uri uri, final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    protected void scrubClass(final Class<?> testCaseClass) throws IllegalAccessException {
        throw new RuntimeException("Stub!");
    }
}
