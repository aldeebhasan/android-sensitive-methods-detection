package android.media;

import android.os.*;

public final class AudioPlaybackConfiguration implements Parcelable
{
    public static final Creator<AudioPlaybackConfiguration> CREATOR;
    
    AudioPlaybackConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioAttributes getAudioAttributes() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
