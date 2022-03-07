package junit.framework;

import java.util.*;

public class TestResult
{
    protected Vector<TestFailure> fErrors;
    protected Vector<TestFailure> fFailures;
    protected Vector<TestListener> fListeners;
    protected int fRunTests;
    
    public TestResult() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void addError(final Test test, final Throwable t) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void addFailure(final Test test, final AssertionFailedError t) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void addListener(final TestListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void removeListener(final TestListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void endTest(final Test test) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized int errorCount() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized Enumeration<TestFailure> errors() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized int failureCount() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized Enumeration<TestFailure> failures() {
        throw new RuntimeException("Stub!");
    }
    
    protected void run(final TestCase test) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized int runCount() {
        throw new RuntimeException("Stub!");
    }
    
    public void runProtected(final Test test, final Protectable p) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized boolean shouldStop() {
        throw new RuntimeException("Stub!");
    }
    
    public void startTest(final Test test) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void stop() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized boolean wasSuccessful() {
        throw new RuntimeException("Stub!");
    }
}
