package android.app;

import android.os.*;

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
