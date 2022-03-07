package android.media;

public static final class CodecException extends IllegalStateException
{
    public static final int ERROR_INSUFFICIENT_RESOURCE = 1100;
    public static final int ERROR_RECLAIMED = 1101;
    
    CodecException() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isTransient() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRecoverable() {
        throw new RuntimeException("Stub!");
    }
    
    public int getErrorCode() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDiagnosticInfo() {
        throw new RuntimeException("Stub!");
    }
}
