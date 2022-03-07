package android.test;

import android.app.*;
import android.os.*;
import junit.framework.*;

@Deprecated
public class InstrumentationTestRunner extends Instrumentation implements TestSuiteProvider
{
    public static final String REPORT_KEY_NAME_CLASS = "class";
    public static final String REPORT_KEY_NAME_TEST = "test";
    public static final String REPORT_KEY_NUM_CURRENT = "current";
    public static final String REPORT_KEY_NUM_TOTAL = "numtests";
    public static final String REPORT_KEY_STACK = "stack";
    public static final String REPORT_VALUE_ID = "InstrumentationTestRunner";
    public static final int REPORT_VALUE_RESULT_ERROR = -1;
    public static final int REPORT_VALUE_RESULT_FAILURE = -2;
    public static final int REPORT_VALUE_RESULT_OK = 0;
    public static final int REPORT_VALUE_RESULT_START = 1;
    
    public InstrumentationTestRunner() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate(final Bundle arguments) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getArguments() {
        throw new RuntimeException("Stub!");
    }
    
    protected AndroidTestRunner getAndroidTestRunner() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public TestSuite getTestSuite() {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuite getAllTests() {
        throw new RuntimeException("Stub!");
    }
    
    public ClassLoader getLoader() {
        throw new RuntimeException("Stub!");
    }
}
