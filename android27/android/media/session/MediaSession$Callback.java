package android.media.session;

import android.os.*;
import android.content.*;
import android.net.*;
import android.media.*;

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
