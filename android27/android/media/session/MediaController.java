package android.media.session;

import android.content.*;
import android.view.*;
import java.util.*;
import android.app.*;
import android.os.*;
import android.net.*;
import android.media.*;

public final class MediaController
{
    public MediaController(final Context context, final MediaSession.Token token) {
        throw new RuntimeException("Stub!");
    }
    
    public TransportControls getTransportControls() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchMediaButtonEvent(final KeyEvent keyEvent) {
        throw new RuntimeException("Stub!");
    }
    
    public PlaybackState getPlaybackState() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaMetadata getMetadata() {
        throw new RuntimeException("Stub!");
    }
    
    public List<MediaSession.QueueItem> getQueue() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getQueueTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRatingType() {
        throw new RuntimeException("Stub!");
    }
    
    public long getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public PlaybackInfo getPlaybackInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public PendingIntent getSessionActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaSession.Token getSessionToken() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVolumeTo(final int value, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void adjustVolume(final int direction, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterCallback(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendCommand(final String command, final Bundle args, final ResultReceiver cb) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
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
    
    public final class TransportControls
    {
        TransportControls() {
            throw new RuntimeException("Stub!");
        }
        
        public void prepare() {
            throw new RuntimeException("Stub!");
        }
        
        public void prepareFromMediaId(final String mediaId, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void prepareFromSearch(final String query, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void prepareFromUri(final Uri uri, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void play() {
            throw new RuntimeException("Stub!");
        }
        
        public void playFromMediaId(final String mediaId, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void playFromSearch(final String query, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void playFromUri(final Uri uri, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void skipToQueueItem(final long id) {
            throw new RuntimeException("Stub!");
        }
        
        public void pause() {
            throw new RuntimeException("Stub!");
        }
        
        public void stop() {
            throw new RuntimeException("Stub!");
        }
        
        public void seekTo(final long pos) {
            throw new RuntimeException("Stub!");
        }
        
        public void fastForward() {
            throw new RuntimeException("Stub!");
        }
        
        public void skipToNext() {
            throw new RuntimeException("Stub!");
        }
        
        public void rewind() {
            throw new RuntimeException("Stub!");
        }
        
        public void skipToPrevious() {
            throw new RuntimeException("Stub!");
        }
        
        public void setRating(final Rating rating) {
            throw new RuntimeException("Stub!");
        }
        
        public void sendCustomAction(final PlaybackState.CustomAction customAction, final Bundle args) {
            throw new RuntimeException("Stub!");
        }
        
        public void sendCustomAction(final String action, final Bundle args) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class PlaybackInfo
    {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        
        PlaybackInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public int getPlaybackType() {
            throw new RuntimeException("Stub!");
        }
        
        public AudioAttributes getAudioAttributes() {
            throw new RuntimeException("Stub!");
        }
        
        public int getVolumeControl() {
            throw new RuntimeException("Stub!");
        }
        
        public int getMaxVolume() {
            throw new RuntimeException("Stub!");
        }
        
        public int getCurrentVolume() {
            throw new RuntimeException("Stub!");
        }
    }
}
