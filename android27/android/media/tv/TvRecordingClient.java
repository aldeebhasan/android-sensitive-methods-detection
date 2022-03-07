package android.media.tv;

import android.content.*;
import android.net.*;
import android.os.*;

public class TvRecordingClient
{
    public TvRecordingClient(final Context context, final String tag, final RecordingCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void tune(final String inputId, final Uri channelUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void tune(final String inputId, final Uri channelUri, final Bundle params) {
        throw new RuntimeException("Stub!");
    }
    
    public void release() {
        throw new RuntimeException("Stub!");
    }
    
    public void startRecording(final Uri programUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void stopRecording() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAppPrivateCommand(final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class RecordingCallback
    {
        public RecordingCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onConnectionFailed(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onDisconnected(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onTuned(final Uri channelUri) {
            throw new RuntimeException("Stub!");
        }
        
        public void onRecordingStopped(final Uri recordedProgramUri) {
            throw new RuntimeException("Stub!");
        }
        
        public void onError(final int error) {
            throw new RuntimeException("Stub!");
        }
    }
}
