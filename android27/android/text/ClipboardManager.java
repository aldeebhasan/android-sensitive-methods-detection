package android.text;

@Deprecated
public abstract class ClipboardManager
{
    public ClipboardManager() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract CharSequence getText();
    
    public abstract void setText(final CharSequence p0);
    
    public abstract boolean hasText();
}
