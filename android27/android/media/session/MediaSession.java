package android.media.session;

import android.app.*;
import java.util.*;
import android.os.*;
import android.content.*;
import android.net.*;
import android.media.*;

public final class MediaSession
{
    @Deprecated
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    @Deprecated
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    
    public MediaSession(final Context context, final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallback(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallback(final Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSessionActivity(final PendingIntent pi) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMediaButtonReceiver(final PendingIntent mbr) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFlags(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlaybackToLocal(final AudioAttributes attributes) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlaybackToRemote(final VolumeProvider volumeProvider) {
        throw new RuntimeException("Stub!");
    }
    
    public void setActive(final boolean active) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActive() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendSessionEvent(final String event, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void release() {
        throw new RuntimeException("Stub!");
    }
    
    public Token getSessionToken() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaController getController() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlaybackState(final PlaybackState state) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMetadata(final MediaMetadata metadata) {
        throw new RuntimeException("Stub!");
    }
    
    public void setQueue(final List<QueueItem> queue) {
        throw new RuntimeException("Stub!");
    }
    
    public void setQueueTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRatingType(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Token implements Parcelable
    {
        public static final Creator<Token> CREATOR;
        
        Token() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object obj) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onCommand(final String command, final Bundle args, final ResultReceiver cb) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean onMediaButtonEvent(final Intent mediaButtonIntent) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPrepare() {
            throw new RuntimeException("Stub!");
        }
        
        public void onPrepareFromMediaId(final String mediaId, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPrepareFromSearch(final String query, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPrepareFromUri(final Uri uri, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPlay() {
            throw new RuntimeException("Stub!");
        }
        
        public void onPlayFromSearch(final String query, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPlayFromMediaId(final String mediaId, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPlayFromUri(final Uri uri, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onSkipToQueueItem(final long id) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPause() {
            throw new RuntimeException("Stub!");
        }
        
        public void onSkipToNext() {
            throw new RuntimeException("Stub!");
        }
        
        public void onSkipToPrevious() {
            throw new RuntimeException("Stub!");
        }
        
        public void onFastForward() {
            throw new RuntimeException("Stub!");
        }
        
        public void onRewind() {
            throw new RuntimeException("Stub!");
        }
        
        public void onStop() {
            throw new RuntimeException("Stub!");
        }
        
        public void onSeekTo(final long pos) {
            throw new RuntimeException("Stub!");
        }
        
        public void onSetRating(final Rating rating) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCustomAction(final String action, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class QueueItem implements Parcelable
    {
        public static final Creator<QueueItem> CREATOR;
        public static final int UNKNOWN_ID = -1;
        
        public QueueItem(final MediaDescription description, final long id) {
            throw new RuntimeException("Stub!");
        }
        
        public MediaDescription getDescription() {
            throw new RuntimeException("Stub!");
        }
        
        public long getQueueId() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object o) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
}
