package android.system;

public final class ErrnoException extends Exception
{
    public final int errno;
    
    public ErrnoException(final String functionName, final int errno) {
        throw new RuntimeException("Stub!");
    }
    
    public ErrnoException(final String functionName, final int errno, final Throwable cause) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getMessage() {
        throw new RuntimeException("Stub!");
    }
}
