package android.media.tv;

import android.net.*;
import java.util.*;

public abstract static class TvInputCallback
{
    public TvInputCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionFailed(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDisconnected(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onChannelRetuned(final String inputId, final Uri channelUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTracksChanged(final String inputId, final List<TvTrackInfo> tracks) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTrackSelected(final String inputId, final int type, final String trackId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoSizeChanged(final String inputId, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoAvailable(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoUnavailable(final String inputId, final int reason) {
        throw new RuntimeException("Stub!");
    }
    
    public void onContentAllowed(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onContentBlocked(final String inputId, final TvContentRating rating) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftStatusChanged(final String inputId, final int status) {
        throw new RuntimeException("Stub!");
    }
}
