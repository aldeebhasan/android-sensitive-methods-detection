package android.media.tv;

import android.content.*;
import android.net.*;
import android.os.*;

public abstract static class RecordingSession
{
    public RecordingSession(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyTuned(final Uri channelUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyRecordingStopped(final Uri recordedProgramUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyError(final int error) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onTune(final Uri p0);
    
    public void onTune(final Uri channelUri, final Bundle params) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onStartRecording(final Uri p0);
    
    public abstract void onStopRecording();
    
    public abstract void onRelease();
    
    public void onAppPrivateCommand(final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
}
