package android.hardware.camera2;

public abstract static class TorchCallback
{
    public TorchCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTorchModeUnavailable(final String cameraId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTorchModeChanged(final String cameraId, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
}
