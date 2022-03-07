package junit.runner;

import java.util.*;
import java.io.*;
import junit.framework.*;

public abstract class BaseTestRunner implements TestListener
{
    public static final String SUITE_METHODNAME = "suite";
    
    public BaseTestRunner() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void startTest(final Test test) {
        throw new RuntimeException("Stub!");
    }
    
    protected static void setPreferences(final Properties preferences) {
        throw new RuntimeException("Stub!");
    }
    
    protected static Properties getPreferences() {
        throw new RuntimeException("Stub!");
    }
    
    public static void savePreferences() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreference(final String key, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void endTest(final Test test) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void addError(final Test test, final Throwable t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void addFailure(final Test test, final AssertionFailedError t) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void testStarted(final String p0);
    
    public abstract void testEnded(final String p0);
    
    public abstract void testFailed(final int p0, final Test p1, final Throwable p2);
    
    public Test getTest(final String suiteClassName) {
        throw new RuntimeException("Stub!");
    }
    
    public String elapsedTimeAsString(final long runTime) {
        throw new RuntimeException("Stub!");
    }
    
    protected String processArguments(final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLoading(final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public String extractClassName(final String className) {
        throw new RuntimeException("Stub!");
    }
    
    public static String truncate(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    protected abstract void runFailed(final String p0);
    
    @Deprecated
    public TestSuiteLoader getLoader() {
        throw new RuntimeException("Stub!");
    }
    
    protected Class<?> loadSuiteClass(final String suiteClassName) throws ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    protected void clearStatus() {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean useReloadingTestSuiteLoader() {
        throw new RuntimeException("Stub!");
    }
    
    public static String getPreference(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getPreference(final String key, final int dflt) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getFilteredTrace(final Throwable t) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static boolean inVAJava() {
        throw new RuntimeException("Stub!");
    }
    
    public static String getFilteredTrace(final String stack) {
        throw new RuntimeException("Stub!");
    }
    
    protected static boolean showStackRaw() {
        throw new RuntimeException("Stub!");
    }
}
