package android.speech;

import android.os.*;

public interface RecognitionListener
{
    void onReadyForSpeech(final Bundle p0);
    
    void onBeginningOfSpeech();
    
    void onRmsChanged(final float p0);
    
    void onBufferReceived(final byte[] p0);
    
    void onEndOfSpeech();
    
    void onError(final int p0);
    
    void onResults(final Bundle p0);
    
    void onPartialResults(final Bundle p0);
    
    void onEvent(final int p0, final Bundle p1);
}
