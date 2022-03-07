package android.hardware.camera2;

public abstract static class AvailabilityCallback
{
    public AvailabilityCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCameraAvailable(final String cameraId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCameraUnavailable(final String cameraId) {
        throw new RuntimeException("Stub!");
    }
}
