package android.service.voice;

import android.os.*;
import android.content.*;
import android.view.*;
import android.app.assist.*;
import android.content.res.*;
import java.io.*;
import android.app.*;
import android.graphics.*;

public class VoiceInteractionSession implements KeyEvent.Callback, ComponentCallbacks2
{
    public static final int SHOW_SOURCE_ACTIVITY = 16;
    public static final int SHOW_SOURCE_APPLICATION = 8;
    public static final int SHOW_SOURCE_ASSIST_GESTURE = 4;
    public static final int SHOW_WITH_ASSIST = 1;
    public static final int SHOW_WITH_SCREENSHOT = 2;
    
    public VoiceInteractionSession(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractionSession(final Context context, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDisabledShowContext(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDisabledShowContext() {
        throw new RuntimeException("Stub!");
    }
    
    public int getUserDisabledShowContext() {
        throw new RuntimeException("Stub!");
    }
    
    public void show(final Bundle args, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void hide() {
        throw new RuntimeException("Stub!");
    }
    
    public void setUiEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTheme(final int theme) {
        throw new RuntimeException("Stub!");
    }
    
    public void startVoiceActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void startAssistantActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setKeepAwake(final boolean keepAwake) {
        throw new RuntimeException("Stub!");
    }
    
    public void closeSystemDialogs() {
        throw new RuntimeException("Stub!");
    }
    
    public LayoutInflater getLayoutInflater() {
        throw new RuntimeException("Stub!");
    }
    
    public Dialog getWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public void finish() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPrepareShow(final Bundle args, final int showFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public void onShow(final Bundle args, final int showFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHide() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public View onCreateContentView() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAssistStructureFailure(final Throwable failure) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHandleAssist(final Bundle data, final AssistStructure structure, final AssistContent content) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHandleAssistSecondary(final Bundle data, final AssistStructure structure, final AssistContent content, final int index, final int count) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHandleScreenshot(final Bitmap screenshot) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyLongPress(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyMultiple(final int keyCode, final int count, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBackPressed() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCloseSystemDialogs() {
        throw new RuntimeException("Stub!");
    }
    
    public void onLockscreenShown() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onLowMemory() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTrimMemory(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public void onComputeInsets(final Insets outInsets) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTaskStarted(final Intent intent, final int taskId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTaskFinished(final Intent intent, final int taskId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean[] onGetSupportedCommands(final String[] commands) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestConfirmation(final ConfirmationRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestPickOption(final PickOptionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestCompleteVoice(final CompleteVoiceRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestAbortVoice(final AbortVoiceRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestCommand(final CommandRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCancelRequest(final Request request) {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Request
    {
        Request() {
            throw new RuntimeException("Stub!");
        }
        
        public int getCallingUid() {
            throw new RuntimeException("Stub!");
        }
        
        public String getCallingPackage() {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isActive() {
            throw new RuntimeException("Stub!");
        }
        
        public void cancel() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
    
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
    
    public static final class CommandRequest extends Request
    {
        CommandRequest() {
            throw new RuntimeException("Stub!");
        }
        
        public String getCommand() {
            throw new RuntimeException("Stub!");
        }
        
        public void sendIntermediateResult(final Bundle result) {
            throw new RuntimeException("Stub!");
        }
        
        public void sendResult(final Bundle result) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Insets
    {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public final Rect contentInsets;
        public int touchableInsets;
        public final Region touchableRegion;
        
        public Insets() {
            throw new RuntimeException("Stub!");
        }
    }
}
