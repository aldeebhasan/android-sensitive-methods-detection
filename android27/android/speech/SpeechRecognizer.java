package android.speech;

import android.content.*;

public class SpeechRecognizer
{
    public static final String CONFIDENCE_SCORES = "confidence_scores";
    public static final int ERROR_AUDIO = 3;
    public static final int ERROR_CLIENT = 5;
    public static final int ERROR_INSUFFICIENT_PERMISSIONS = 9;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_NETWORK_TIMEOUT = 1;
    public static final int ERROR_NO_MATCH = 7;
    public static final int ERROR_RECOGNIZER_BUSY = 8;
    public static final int ERROR_SERVER = 4;
    public static final int ERROR_SPEECH_TIMEOUT = 6;
    public static final String RESULTS_RECOGNITION = "results_recognition";
    
    SpeechRecognizer() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isRecognitionAvailable(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static SpeechRecognizer createSpeechRecognizer(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static SpeechRecognizer createSpeechRecognizer(final Context context, final ComponentName serviceComponent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRecognitionListener(final RecognitionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void startListening(final Intent recognizerIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public void stopListening() {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void destroy() {
        throw new RuntimeException("Stub!");
    }
}
