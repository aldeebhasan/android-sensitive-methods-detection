package android.service.voice;

import android.app.*;
import android.os.*;

public static final class CompleteVoiceRequest extends Request
{
    CompleteVoiceRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractor.Prompt getVoicePrompt() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public CharSequence getMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendCompleteResult(final Bundle result) {
        throw new RuntimeException("Stub!");
    }
}
