package android.hardware.camera2;

import android.view.*;
import java.util.*;
import android.hardware.camera2.params.*;
import android.os.*;

public abstract class CameraCaptureSession implements AutoCloseable
{
    public CameraCaptureSession() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract CameraDevice getDevice();
    
    public abstract void prepare(final Surface p0) throws CameraAccessException;
    
    public abstract void finalizeOutputConfigurations(final List<OutputConfiguration> p0) throws CameraAccessException;
    
    public abstract int capture(final CaptureRequest p0, final CaptureCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract int captureBurst(final List<CaptureRequest> p0, final CaptureCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract int setRepeatingRequest(final CaptureRequest p0, final CaptureCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract int setRepeatingBurst(final List<CaptureRequest> p0, final CaptureCallback p1, final Handler p2) throws CameraAccessException;
    
    public abstract void stopRepeating() throws CameraAccessException;
    
    public abstract void abortCaptures() throws CameraAccessException;
    
    public abstract boolean isReprocessable();
    
    public abstract Surface getInputSurface();
    
    @Override
    public abstract void close();
    
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
    
    public abstract static class CaptureCallback
    {
        public CaptureCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureStarted(final CameraCaptureSession session, final CaptureRequest request, final long timestamp, final long frameNumber) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureProgressed(final CameraCaptureSession session, final CaptureRequest request, final CaptureResult partialResult) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureCompleted(final CameraCaptureSession session, final CaptureRequest request, final TotalCaptureResult result) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureFailed(final CameraCaptureSession session, final CaptureRequest request, final CaptureFailure failure) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureSequenceCompleted(final CameraCaptureSession session, final int sequenceId, final long frameNumber) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureSequenceAborted(final CameraCaptureSession session, final int sequenceId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCaptureBufferLost(final CameraCaptureSession session, final CaptureRequest request, final Surface target, final long frameNumber) {
            throw new RuntimeException("Stub!");
        }
    }
}
