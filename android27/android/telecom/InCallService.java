package android.telecom;

import android.app.*;
import android.content.*;
import java.util.*;
import android.os.*;
import android.view.*;
import android.net.*;

public abstract class InCallService extends Service
{
    public static final String SERVICE_INTERFACE = "android.telecom.InCallService";
    
    public InCallService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onUnbind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public final List<Call> getCalls() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean canAddCall() {
        throw new RuntimeException("Stub!");
    }
    
    public final CallAudioState getCallAudioState() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setMuted(final boolean state) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setAudioRoute(final int route) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallAudioStateChanged(final CallAudioState audioState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBringToForeground(final boolean showDialpad) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallAdded(final Call call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallRemoved(final Call call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCanAddCallChanged(final boolean canAddCall) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSilenceRinger() {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionEvent(final Call call, final String event, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
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
}
