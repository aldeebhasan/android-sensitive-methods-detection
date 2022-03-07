package android.hardware.camera2;

public abstract static class StateCallback
{
    public static final int ERROR_CAMERA_DEVICE = 4;
    public static final int ERROR_CAMERA_DISABLED = 3;
    public static final int ERROR_CAMERA_IN_USE = 1;
    public static final int ERROR_CAMERA_SERVICE = 5;
    public static final int ERROR_MAX_CAMERAS_IN_USE = 2;
    
    public StateCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onOpened(final CameraDevice p0);
    
    public void onClosed(final CameraDevice camera) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onDisconnected(final CameraDevice p0);
    
    public abstract void onError(final CameraDevice p0, final int p1);
}
