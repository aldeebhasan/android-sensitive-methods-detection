package android.media;

import java.util.*;

public abstract static class AudioPlaybackCallback
{
    public AudioPlaybackCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPlaybackConfigChanged(final List<AudioPlaybackConfiguration> configs) {
        throw new RuntimeException("Stub!");
    }
}
