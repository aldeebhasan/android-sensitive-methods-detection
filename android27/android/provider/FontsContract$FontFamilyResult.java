package android.provider;

public static class FontFamilyResult
{
    public static final int STATUS_OK = 0;
    public static final int STATUS_REJECTED = 3;
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    
    FontFamilyResult() {
        throw new RuntimeException("Stub!");
    }
    
    public int getStatusCode() {
        throw new RuntimeException("Stub!");
    }
    
    public FontInfo[] getFonts() {
        throw new RuntimeException("Stub!");
    }
}
