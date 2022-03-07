package android.media;

public static final class CryptoException extends RuntimeException
{
    public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
    public static final int ERROR_KEY_EXPIRED = 2;
    public static final int ERROR_NO_KEY = 1;
    public static final int ERROR_RESOURCE_BUSY = 3;
    public static final int ERROR_SESSION_NOT_OPENED = 5;
    public static final int ERROR_UNSUPPORTED_OPERATION = 6;
    
    public CryptoException(final int errorCode, final String detailMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public int getErrorCode() {
        throw new RuntimeException("Stub!");
    }
}
