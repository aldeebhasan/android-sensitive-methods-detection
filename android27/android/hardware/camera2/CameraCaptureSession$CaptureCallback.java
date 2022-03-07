package android.hardware.camera2;

import android.view.*;

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
