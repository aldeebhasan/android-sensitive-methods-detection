package android.media;

import android.os.*;

public final class AudioFocusRequest
{
    AudioFocusRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioAttributes getAudioAttributes() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFocusGain() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean willPauseWhenDucked() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean acceptsDelayedFocusGain() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Builder
    {
        public Builder(final int focusGain) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final AudioFocusRequest requestToCopy) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setFocusGain(final int focusGain) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOnAudioFocusChangeListener(final AudioManager.OnAudioFocusChangeListener listener) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOnAudioFocusChangeListener(final AudioManager.OnAudioFocusChangeListener listener, final Handler handler) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAudioAttributes(final AudioAttributes attributes) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setWillPauseWhenDucked(final boolean pauseOnDuck) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAcceptsDelayedFocusGain(final boolean acceptsDelayedFocusGain) {
            throw new RuntimeException("Stub!");
        }
        
        public AudioFocusRequest build() {
            throw new RuntimeException("Stub!");
        }
    }
}
