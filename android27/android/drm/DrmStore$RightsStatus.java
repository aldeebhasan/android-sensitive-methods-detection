package android.drm;

public static class RightsStatus
{
    public static final int RIGHTS_EXPIRED = 2;
    public static final int RIGHTS_INVALID = 1;
    public static final int RIGHTS_NOT_ACQUIRED = 3;
    public static final int RIGHTS_VALID = 0;
    
    public RightsStatus() {
        throw new RuntimeException("Stub!");
    }
}
