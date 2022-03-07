package android.telecom;

import android.os.*;
import android.view.*;
import android.net.*;

public abstract static class VideoCall
{
    public VideoCall() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void registerCallback(final Callback p0);
    
    public abstract void registerCallback(final Callback p0, final Handler p1);
    
    public abstract void unregisterCallback(final Callback p0);
    
    public abstract void setCamera(final String p0);
    
    public abstract void setPreviewSurface(final Surface p0);
    
    public abstract void setDisplaySurface(final Surface p0);
    
    public abstract void setDeviceOrientation(final int p0);
    
    public abstract void setZoom(final float p0);
    
    public abstract void sendSessionModifyRequest(final VideoProfile p0);
    
    public abstract void sendSessionModifyResponse(final VideoProfile p0);
    
    public abstract void requestCameraCapabilities();
    
    public abstract void requestCallDataUsage();
    
    public abstract void setPauseImage(final Uri p0);
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onSessionModifyRequestReceived(final VideoProfile p0);
        
        public abstract void onSessionModifyResponseReceived(final int p0, final VideoProfile p1, final VideoProfile p2);
        
        public abstract void onCallSessionEvent(final int p0);
        
        public abstract void onPeerDimensionsChanged(final int p0, final int p1);
        
        public abstract void onVideoQualityChanged(final int p0);
        
        public abstract void onCallDataUsageChanged(final long p0);
        
        public abstract void onCameraCapabilitiesChanged(final VideoProfile.CameraCapabilities p0);
    }
}
