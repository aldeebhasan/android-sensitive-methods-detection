package android.test;

@Deprecated
public class ComparisonFailure extends AssertionFailedError
{
    public ComparisonFailure(final String message, final String expected, final String actual) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getMessage() {
        throw new RuntimeException("Stub!");
    }
}
