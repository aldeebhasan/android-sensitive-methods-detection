package android.service.voice;

import android.app.*;
import android.os.*;

public static final class ConfirmationRequest extends Request
{
    ConfirmationRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractor.Prompt getVoicePrompt() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public CharSequence getPrompt() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendConfirmationResult(final boolean confirmed, final Bundle result) {
        throw new RuntimeException("Stub!");
    }
}
