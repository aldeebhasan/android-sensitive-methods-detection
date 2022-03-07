package android.view.textservice;

public class SpellCheckerSession
{
    public static final String SERVICE_META_DATA = "android.view.textservice.scs";
    
    SpellCheckerSession() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSessionDisconnected() {
        throw new RuntimeException("Stub!");
    }
    
    public SpellCheckerInfo getSpellChecker() {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void getSentenceSuggestions(final TextInfo[] textInfos, final int suggestionsLimit) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void getSuggestions(final TextInfo textInfo, final int suggestionsLimit) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void getSuggestions(final TextInfo[] textInfos, final int suggestionsLimit, final boolean sequentialWords) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public interface SpellCheckerSessionListener
    {
        void onGetSuggestions(final SuggestionsInfo[] p0);
        
        void onGetSentenceSuggestions(final SentenceSuggestionsInfo[] p0);
    }
}
