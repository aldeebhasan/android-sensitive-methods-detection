package android.media.tv;

import android.net.*;

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
