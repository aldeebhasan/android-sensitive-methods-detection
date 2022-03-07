package android.media;

import android.os.*;

public final class AudioRecordingConfiguration implements Parcelable
{
    public static final Creator<AudioRecordingConfiguration> CREATOR;
    
    AudioRecordingConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public int getClientAudioSource() {
        throw new RuntimeException("Stub!");
    }
    
    public int getClientAudioSessionId() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioFormat getFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioFormat getClientFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioDeviceInfo getAudioDevice() {
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
