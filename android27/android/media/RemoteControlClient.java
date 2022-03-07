package android.media;

import android.app.*;
import android.os.*;
import android.media.session.*;
import android.graphics.*;

@Deprecated
public class RemoteControlClient
{
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_POSITION_UPDATE = 256;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_RATING = 512;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static final int PLAYSTATE_BUFFERING = 8;
    public static final int PLAYSTATE_ERROR = 9;
    public static final int PLAYSTATE_FAST_FORWARDING = 4;
    public static final int PLAYSTATE_PAUSED = 2;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_REWINDING = 5;
    public static final int PLAYSTATE_SKIPPING_BACKWARDS = 7;
    public static final int PLAYSTATE_SKIPPING_FORWARDS = 6;
    public static final int PLAYSTATE_STOPPED = 1;
    
    public RemoteControlClient(final PendingIntent mediaButtonIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteControlClient(final PendingIntent mediaButtonIntent, final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public MediaSession getMediaSession() {
        throw new RuntimeException("Stub!");
    }
    
    public MetadataEditor editMetadata(final boolean startEmpty) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlaybackState(final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlaybackState(final int state, final long timeInMs, final float playbackSpeed) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTransportControlFlags(final int transportControlFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMetadataUpdateListener(final OnMetadataUpdateListener l) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlaybackPositionUpdateListener(final OnPlaybackPositionUpdateListener l) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnGetPlaybackPositionListener(final OnGetPlaybackPositionListener l) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public class MetadataEditor extends MediaMetadataEditor
    {
        public static final int BITMAP_KEY_ARTWORK = 100;
        
        MetadataEditor() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized MetadataEditor putString(final int key, final String value) throws IllegalArgumentException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized MetadataEditor putLong(final int key, final long value) throws IllegalArgumentException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized MetadataEditor putBitmap(final int key, final Bitmap bitmap) throws IllegalArgumentException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized MetadataEditor putObject(final int key, final Object object) throws IllegalArgumentException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized void clear() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized void apply() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnGetPlaybackPositionListener
    {
        long onGetPlaybackPosition();
    }
    
    public interface OnPlaybackPositionUpdateListener
    {
        void onPlaybackPositionUpdate(final long p0);
    }
    
    public interface OnMetadataUpdateListener
    {
        void onMetadataUpdate(final int p0, final Object p1);
    }
}
