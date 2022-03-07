package android.app;

import android.content.*;
import android.os.*;

public final class VoiceInteractor
{
    VoiceInteractor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean submitRequest(final Request request) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean submitRequest(final Request request, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public Request[] getActiveRequests() {
        throw new RuntimeException("Stub!");
    }
    
    public Request getActiveRequest(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean[] supportsCommands(final String[] commands) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Request
    {
        Request() {
            throw new RuntimeException("Stub!");
        }
        
        public String getName() {
            throw new RuntimeException("Stub!");
        }
        
        public void cancel() {
            throw new RuntimeException("Stub!");
        }
        
        public Context getContext() {
            throw new RuntimeException("Stub!");
        }
        
        public Activity getActivity() {
            throw new RuntimeException("Stub!");
        }
        
        public void onCancel() {
            throw new RuntimeException("Stub!");
        }
        
        public void onAttached(final Activity activity) {
            throw new RuntimeException("Stub!");
        }
        
        public void onDetached() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class ConfirmationRequest extends Request
    {
        public ConfirmationRequest(final Prompt prompt, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onConfirmationResult(final boolean confirmed, final Bundle result) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class PickOptionRequest extends Request
    {
        public PickOptionRequest(final Prompt prompt, final Option[] options, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onPickOptionResult(final boolean finished, final Option[] selections, final Bundle result) {
            throw new RuntimeException("Stub!");
        }
        
        public static final class Option implements Parcelable
        {
            public static final Creator<Option> CREATOR;
            
            public Option(final CharSequence label, final int index) {
                throw new RuntimeException("Stub!");
            }
            
            public Option addSynonym(final CharSequence synonym) {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getLabel() {
                throw new RuntimeException("Stub!");
            }
            
            public int getIndex() {
                throw new RuntimeException("Stub!");
            }
            
            public int countSynonyms() {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getSynonymAt(final int index) {
                throw new RuntimeException("Stub!");
            }
            
            public void setExtras(final Bundle extras) {
                throw new RuntimeException("Stub!");
            }
            
            public Bundle getExtras() {
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
            
            static {
                CREATOR = null;
            }
        }
    }
    
    public static class CompleteVoiceRequest extends Request
    {
        public CompleteVoiceRequest(final Prompt prompt, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCompleteResult(final Bundle result) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class AbortVoiceRequest extends Request
    {
        public AbortVoiceRequest(final Prompt prompt, final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public void onAbortResult(final Bundle result) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class CommandRequest extends Request
    {
        public CommandRequest(final String command, final Bundle args) {
            throw new RuntimeException("Stub!");
        }
        
        public void onCommandResult(final boolean isCompleted, final Bundle result) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class Prompt implements Parcelable
    {
        public static final Creator<Prompt> CREATOR;
        
        public Prompt(final CharSequence[] voicePrompts, final CharSequence visualPrompt) {
            throw new RuntimeException("Stub!");
        }
        
        public Prompt(final CharSequence prompt) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getVoicePromptAt(final int index) {
            throw new RuntimeException("Stub!");
        }
        
        public int countVoicePrompts() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getVisualPrompt() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
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
        
        static {
            CREATOR = null;
        }
    }
}
