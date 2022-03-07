package android.widget;

public interface Tokenizer
{
    int findTokenStart(final CharSequence p0, final int p1);
    
    int findTokenEnd(final CharSequence p0, final int p1);
    
    CharSequence terminateToken(final CharSequence p0);
}
