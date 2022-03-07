package android.service.textservice;

import android.app.*;
import android.content.*;
import android.view.textservice.*;
import android.os.*;

public abstract class SpellCheckerService extends Service
{
    public static final String SERVICE_INTERFACE = "android.service.textservice.SpellCheckerService";
    
    public SpellCheckerService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Session createSession();
    
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
}
