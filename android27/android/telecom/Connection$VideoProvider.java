package android.telecom;

import android.view.*;
import android.net.*;

public abstract static class VideoProvider
{
    public static final int SESSION_EVENT_CAMERA_FAILURE = 5;
    public static final int SESSION_EVENT_CAMERA_PERMISSION_ERROR = 7;
    public static final int SESSION_EVENT_CAMERA_READY = 6;
    public static final int SESSION_EVENT_RX_PAUSE = 1;
    public static final int SESSION_EVENT_RX_RESUME = 2;
    public static final int SESSION_EVENT_TX_START = 3;
    public static final int SESSION_EVENT_TX_STOP = 4;
    public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
    public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
    public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
    public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
    public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;
    
    public VideoProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onSetCamera(final String p0);
    
    public abstract void onSetPreviewSurface(final Surface p0);
    
    public abstract void onSetDisplaySurface(final Surface p0);
    
    public abstract void onSetDeviceOrientation(final int p0);
    
    public abstract void onSetZoom(final float p0);
    
    public abstract void onSendSessionModifyRequest(final VideoProfile p0, final VideoProfile p1);
    
    public abstract void onSendSessionModifyResponse(final VideoProfile p0);
    
    public abstract void onRequestCameraCapabilities();
    
    public abstract void onRequestConnectionDataUsage();
    
    public abstract void onSetPauseImage(final Uri p0);
    
    public void receiveSessionModifyRequest(final VideoProfile videoProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void receiveSessionModifyResponse(final int status, final VideoProfile requestedProfile, final VideoProfile responseProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void handleCallSessionEvent(final int event) {
        throw new RuntimeException("Stub!");
    }
    
    public void changePeerDimensions(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallDataUsage(final long dataUsage) {
        throw new RuntimeException("Stub!");
    }
    
    public void changeCameraCapabilities(final VideoProfile.CameraCapabilities cameraCapabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public void changeVideoQuality(final int videoQuality) {
        throw new RuntimeException("Stub!");
    }
}
