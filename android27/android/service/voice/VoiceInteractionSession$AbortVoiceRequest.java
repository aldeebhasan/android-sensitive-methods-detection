package android.service.voice;

import android.app.*;
import android.os.*;

public static final class AbortVoiceRequest extends Request
{
    AbortVoiceRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractor.Prompt getVoicePrompt() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public CharSequence getMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAbortResult(final Bundle result) {
        throw new RuntimeException("Stub!");
    }
}
