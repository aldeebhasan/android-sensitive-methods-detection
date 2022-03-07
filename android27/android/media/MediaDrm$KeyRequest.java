package android.media;

public static final class KeyRequest
{
    public static final int REQUEST_TYPE_INITIAL = 0;
    public static final int REQUEST_TYPE_RELEASE = 2;
    public static final int REQUEST_TYPE_RENEWAL = 1;
    
    KeyRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getData() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDefaultUrl() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRequestType() {
        throw new RuntimeException("Stub!");
    }
}
