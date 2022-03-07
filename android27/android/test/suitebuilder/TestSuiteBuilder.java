package android.test.suitebuilder;

import java.util.*;
import com.android.internal.util.*;
import junit.framework.*;

@Deprecated
public class TestSuiteBuilder
{
    public TestSuiteBuilder(final Class clazz) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuiteBuilder(final String name, final ClassLoader classLoader) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuiteBuilder includePackages(final String... packageNames) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuiteBuilder excludePackages(final String... packageNames) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuiteBuilder addRequirements(final List<Predicate<TestMethod>> predicates) {
        throw new RuntimeException("Stub!");
    }
    
    public final TestSuiteBuilder includeAllPackagesUnderHere() {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuiteBuilder named(final String newSuiteName) {
        throw new RuntimeException("Stub!");
    }
    
    public final TestSuite build() {
        throw new RuntimeException("Stub!");
    }
    
    protected String getSuiteName() {
        throw new RuntimeException("Stub!");
    }
    
    public final TestSuiteBuilder addRequirements(final Predicate<TestMethod>... predicates) {
        throw new RuntimeException("Stub!");
    }
    
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
}
