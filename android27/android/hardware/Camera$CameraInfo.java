package android.hardware;

@Deprecated
public static class CameraInfo
{
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_FRONT = 1;
    public boolean canDisableShutterSound;
    public int facing;
    public int orientation;
    
    public CameraInfo() {
        throw new RuntimeException("Stub!");
    }
}
