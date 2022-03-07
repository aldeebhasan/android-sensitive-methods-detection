package junit.framework;

public abstract class TestCase extends Assert implements Test
{
    public TestCase() {
        throw new RuntimeException("Stub!");
    }
    
    public TestCase(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int countTestCases() {
        throw new RuntimeException("Stub!");
    }
    
    protected TestResult createResult() {
        throw new RuntimeException("Stub!");
    }
    
    public TestResult run() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void run(final TestResult result) {
        throw new RuntimeException("Stub!");
    }
    
    public void runBare() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    protected void runTest() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setName(final String name) {
        throw new RuntimeException("Stub!");
    }
}
