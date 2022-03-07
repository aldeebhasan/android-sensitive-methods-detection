package android.app;

public class UiModeManager
{
    public static String ACTION_ENTER_CAR_MODE;
    public static String ACTION_ENTER_DESK_MODE;
    public static String ACTION_EXIT_CAR_MODE;
    public static String ACTION_EXIT_DESK_MODE;
    public static final int DISABLE_CAR_MODE_GO_HOME = 1;
    public static final int ENABLE_CAR_MODE_ALLOW_SLEEP = 2;
    public static final int ENABLE_CAR_MODE_GO_CAR_HOME = 1;
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    
    UiModeManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void enableCarMode(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void disableCarMode(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public int getCurrentModeType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNightMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getNightMode() {
        throw new RuntimeException("Stub!");
    }
}
