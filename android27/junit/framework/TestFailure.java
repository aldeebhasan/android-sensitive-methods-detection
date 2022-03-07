package junit.framework;

public class TestFailure
{
    protected Test fFailedTest;
    protected Throwable fThrownException;
    
    public TestFailure(final Test failedTest, final Throwable thrownException) {
        throw new RuntimeException("Stub!");
    }
    
    public Test failedTest() {
        throw new RuntimeException("Stub!");
    }
    
    public Throwable thrownException() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public String trace() {
        throw new RuntimeException("Stub!");
    }
    
    public String exceptionMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFailure() {
        throw new RuntimeException("Stub!");
    }
}
