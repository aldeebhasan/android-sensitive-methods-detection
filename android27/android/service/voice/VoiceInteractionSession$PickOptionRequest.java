package android.service.voice;

import android.app.*;
import android.os.*;

public static final class PickOptionRequest extends Request
{
    PickOptionRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractor.Prompt getVoicePrompt() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public CharSequence getPrompt() {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractor.PickOptionRequest.Option[] getOptions() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendIntermediatePickOptionResult(final VoiceInteractor.PickOptionRequest.Option[] selections, final Bundle result) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendPickOptionResult(final VoiceInteractor.PickOptionRequest.Option[] selections, final Bundle result) {
        throw new RuntimeException("Stub!");
    }
}
