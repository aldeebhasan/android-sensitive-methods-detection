package android.hardware.camera2;

import java.util.*;
import android.view.*;
import android.os.*;
import android.hardware.camera2.params.*;

public abstract class CameraDevice implements AutoCloseable
{
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;
    
    CameraDevice() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract String getId();
    
    public abstract void createCaptureSession(final List<Surface> p0, final CameraCaptureSession.StateCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract void createCaptureSessionByOutputConfigurations(final List<OutputConfiguration> p0, final CameraCaptureSession.StateCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract void createReprocessableCaptureSession(final InputConfiguration p0, final List<Surface> p1, final CameraCaptureSession.StateCallback p2, final Handler p3) throws CameraAccessException;
    
    public abstract void createReprocessableCaptureSessionByConfigurations(final InputConfiguration p0, final List<OutputConfiguration> p1, final CameraCaptureSession.StateCallback p2, final Handler p3) throws CameraAccessException;
    
    public abstract void createConstrainedHighSpeedCaptureSession(final List<Surface> p0, final CameraCaptureSession.StateCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract CaptureRequest.Builder createCaptureRequest(final int p0) throws CameraAccessException;
    
    public abstract CaptureRequest.Builder createReprocessCaptureRequest(final TotalCaptureResult p0) throws CameraAccessException;
    
    @Override
    public abstract void close();
    
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
}
