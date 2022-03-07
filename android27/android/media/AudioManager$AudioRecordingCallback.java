package android.media;

import java.util.*;

public abstract static class AudioRecordingCallback
{
    public AudioRecordingCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onRecordingConfigChanged(final List<AudioRecordingConfiguration> configs) {
        throw new RuntimeException("Stub!");
    }
}
