package android.media;

import android.os.*;
import android.view.*;
import java.nio.*;

public final class MediaSync
{
    public static final int MEDIASYNC_ERROR_AUDIOTRACK_FAIL = 1;
    public static final int MEDIASYNC_ERROR_SURFACE_FAIL = 2;
    
    public MediaSync() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
    
    public final void release() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallback(final Callback cb, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnErrorListener(final OnErrorListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAudioTrack(final AudioTrack audioTrack) {
        throw new RuntimeException("Stub!");
    }
    
    public final native Surface createInputSurface();
    
    public void setPlaybackParams(final PlaybackParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public native PlaybackParams getPlaybackParams();
    
    public void setSyncParams(final SyncParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public native SyncParams getSyncParams();
    
    public void flush() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaTimestamp getTimestamp() {
        throw new RuntimeException("Stub!");
    }
    
    public void queueAudio(final ByteBuffer audioData, final int bufferId, final long presentationTimeUs) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onAudioBufferConsumed(final MediaSync p0, final ByteBuffer p1, final int p2);
    }
    
    public interface OnErrorListener
    {
        void onError(final MediaSync p0, final int p1, final int p2);
    }
}
