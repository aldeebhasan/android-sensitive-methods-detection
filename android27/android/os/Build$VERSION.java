package android.os;

public static class VERSION
{
    public static final String BASE_OS;
    public static final String CODENAME;
    public static final String INCREMENTAL;
    public static final int PREVIEW_SDK_INT;
    public static final String RELEASE;
    @Deprecated
    public static final String SDK;
    public static final int SDK_INT;
    public static final String SECURITY_PATCH;
    
    public VERSION() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        BASE_OS = null;
        CODENAME = null;
        INCREMENTAL = null;
        PREVIEW_SDK_INT = 0;
        RELEASE = null;
        SDK = null;
        SDK_INT = 0;
        SECURITY_PATCH = null;
    }
}
