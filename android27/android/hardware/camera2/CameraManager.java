package android.hardware.camera2;

import android.os.*;

public final class CameraManager
{
    CameraManager() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getCameraIdList() throws CameraAccessException {
        throw new RuntimeException("Stub!");
    }
    
    public void registerAvailabilityCallback(final AvailabilityCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterAvailabilityCallback(final AvailabilityCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerTorchCallback(final TorchCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterTorchCallback(final TorchCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public CameraCharacteristics getCameraCharacteristics(final String cameraId) throws CameraAccessException {
        throw new RuntimeException("Stub!");
    }
    
    public void openCamera(final String cameraId, final CameraDevice.StateCallback callback, final Handler handler) throws CameraAccessException {
        throw new RuntimeException("Stub!");
    }
    
    public void setTorchMode(final String cameraId, final boolean enabled) throws CameraAccessException {
        throw new RuntimeException("Stub!");
    }
    
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
}
