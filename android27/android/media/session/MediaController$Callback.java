package android.media.session;

import android.os.*;
import android.media.*;
import java.util.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionDestroyed() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionEvent(final String event, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPlaybackStateChanged(final PlaybackState state) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMetadataChanged(final MediaMetadata metadata) {
        throw new RuntimeException("Stub!");
    }
    
    public void onQueueChanged(final List<MediaSession.QueueItem> queue) {
        throw new RuntimeException("Stub!");
    }
    
    public void onQueueTitleChanged(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public void onExtrasChanged(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAudioInfoChanged(final PlaybackInfo info) {
        throw new RuntimeException("Stub!");
    }
}
