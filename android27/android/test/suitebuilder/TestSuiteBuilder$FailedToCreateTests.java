package android.test.suitebuilder;

import junit.framework.*;

@Deprecated
public static class FailedToCreateTests extends TestCase
{
    public FailedToCreateTests(final Exception exception) {
        throw new RuntimeException("Stub!");
    }
    
    public void testSuiteConstructionFailed() {
        throw new RuntimeException("Stub!");
    }
}
