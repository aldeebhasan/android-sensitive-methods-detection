package android.media.session;

import android.media.*;

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
