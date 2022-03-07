package android.media;

public static final class KeyStatus
{
    public static final int STATUS_EXPIRED = 1;
    public static final int STATUS_INTERNAL_ERROR = 4;
    public static final int STATUS_OUTPUT_NOT_ALLOWED = 2;
    public static final int STATUS_PENDING = 3;
    public static final int STATUS_USABLE = 0;
    
    KeyStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public int getStatusCode() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getKeyId() {
        throw new RuntimeException("Stub!");
    }
}
