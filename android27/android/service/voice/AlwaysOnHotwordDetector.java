package android.service.voice;

import android.content.*;
import android.media.*;

public class AlwaysOnHotwordDetector
{
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int STATE_HARDWARE_UNAVAILABLE = -2;
    public static final int STATE_KEYPHRASE_ENROLLED = 2;
    public static final int STATE_KEYPHRASE_UNENROLLED = 1;
    public static final int STATE_KEYPHRASE_UNSUPPORTED = -1;
    
    AlwaysOnHotwordDetector() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSupportedRecognitionModes() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean startRecognition(final int recognitionFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean stopRecognition() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createEnrollIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createUnEnrollIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createReEnrollIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public static class EventPayload
    {
        EventPayload() {
            throw new RuntimeException("Stub!");
        }
        
        public AudioFormat getCaptureAudioFormat() {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] getTriggerAudio() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onAvailabilityChanged(final int p0);
        
        public abstract void onDetected(final EventPayload p0);
        
        public abstract void onError();
        
        public abstract void onRecognitionPaused();
        
        public abstract void onRecognitionResumed();
    }
}
