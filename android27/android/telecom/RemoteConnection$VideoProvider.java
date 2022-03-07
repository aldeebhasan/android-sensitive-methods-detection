package android.telecom;

import android.view.*;
import android.net.*;

public static class VideoProvider
{
    VideoProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final Callback l) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterCallback(final Callback l) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCamera(final String cameraId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreviewSurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDisplaySurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDeviceOrientation(final int rotation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZoom(final float value) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendSessionModifyRequest(final VideoProfile fromProfile, final VideoProfile toProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendSessionModifyResponse(final VideoProfile responseProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestCameraCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public void requestCallDataUsage() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPauseImage(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onSessionModifyRequestReceived(final VideoProvider videoProvider, final VideoProfile videoProfile) {
            throw new RuntimeException("Stub!");
        }
        
        public void onSessionModifyResponseReceived(final VideoProvider videoProvider, final int status, final VideoProfile requestedProfile, final VideoProfile responseProfile) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCallSessionEvent(final VideoProvider videoProvider, final int event) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPeerDimensionsChanged(final VideoProvider videoProvider, final int width, final int height) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCallDataUsageChanged(final VideoProvider videoProvider, final long dataUsage) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCameraCapabilitiesChanged(final VideoProvider videoProvider, final VideoProfile.CameraCapabilities cameraCapabilities) {
            throw new RuntimeException("Stub!");
        }
        
        public void onVideoQualityChanged(final VideoProvider videoProvider, final int videoQuality) {
            throw new RuntimeException("Stub!");
        }
    }
}
