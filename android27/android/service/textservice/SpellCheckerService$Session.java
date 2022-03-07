package android.service.textservice;

import android.view.textservice.*;
import android.os.*;

public abstract static class Session
{
    public Session() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onCreate();
    
    public abstract SuggestionsInfo onGetSuggestions(final TextInfo p0, final int p1);
    
    public SuggestionsInfo[] onGetSuggestionsMultiple(final TextInfo[] textInfos, final int suggestionsLimit, final boolean sequentialWords) {
        throw new RuntimeException("Stub!");
    }
    
    public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(final TextInfo[] textInfos, final int suggestionsLimit) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void onClose() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getBundle() {
        throw new RuntimeException("Stub!");
    }
}
