package android.test;

import android.app.*;
import junit.framework.*;

@Deprecated
public class InstrumentationTestSuite extends TestSuite
{
    public InstrumentationTestSuite(final Instrumentation instr) {
        throw new RuntimeException("Stub!");
    }
    
    public InstrumentationTestSuite(final String name, final Instrumentation instr) {
        throw new RuntimeException("Stub!");
    }
    
    public InstrumentationTestSuite(final Class theClass, final Instrumentation instr) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addTestSuite(final Class testClass) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void runTest(final Test test, final TestResult result) {
        throw new RuntimeException("Stub!");
    }
}
