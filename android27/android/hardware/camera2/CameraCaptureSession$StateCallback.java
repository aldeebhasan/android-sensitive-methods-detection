package android.hardware.camera2;

import android.view.*;

public abstract static class StateCallback
{
    public StateCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onConfigured(final CameraCaptureSession p0);
    
    public abstract void onConfigureFailed(final CameraCaptureSession p0);
    
    public void onReady(final CameraCaptureSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onActive(final CameraCaptureSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCaptureQueueEmpty(final CameraCaptureSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onClosed(final CameraCaptureSession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSurfacePrepared(final CameraCaptureSession session, final Surface surface) {
        throw new RuntimeException("Stub!");
    }
}
