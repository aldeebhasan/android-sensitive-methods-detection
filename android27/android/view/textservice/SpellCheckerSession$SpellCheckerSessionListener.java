package android.view.textservice;

public interface SpellCheckerSessionListener
{
    void onGetSuggestions(final SuggestionsInfo[] p0);
    
    void onGetSentenceSuggestions(final SentenceSuggestionsInfo[] p0);
}
