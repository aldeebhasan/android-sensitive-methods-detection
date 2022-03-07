package android.test;

import junit.runner.*;
import java.util.*;
import junit.framework.*;
import android.content.*;
import android.app.*;

@Deprecated
public class AndroidTestRunner extends BaseTestRunner
{
    public AndroidTestRunner() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTestClassName(final String testClassName, final String testMethodName) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTest(final Test test) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearTestListeners() {
        throw new RuntimeException("Stub!");
    }
    
    public void addTestListener(final TestListener testListener) {
        throw new RuntimeException("Stub!");
    }
    
    protected TestResult createTestResult() {
        throw new RuntimeException("Stub!");
    }
    
    public List<TestCase> getTestCases() {
        throw new RuntimeException("Stub!");
    }
    
    public String getTestClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public TestResult getTestResult() {
        throw new RuntimeException("Stub!");
    }
    
    public void runTest() {
        throw new RuntimeException("Stub!");
    }
    
    public void runTest(final TestResult testResult) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContext(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInstrumentation(final Instrumentation instrumentation) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setInstrumentaiton(final Instrumentation instrumentation) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Class loadSuiteClass(final String suiteClassName) throws ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void testStarted(final String testName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void testEnded(final String testName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void testFailed(final int status, final Test test, final Throwable t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void runFailed(final String message) {
        throw new RuntimeException("Stub!");
    }
}
