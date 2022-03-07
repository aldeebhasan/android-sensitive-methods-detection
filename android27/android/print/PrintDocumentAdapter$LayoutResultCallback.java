package android.print;

public abstract static class LayoutResultCallback
{
    LayoutResultCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onLayoutFinished(final PrintDocumentInfo info, final boolean changed) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLayoutFailed(final CharSequence error) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLayoutCancelled() {
        throw new RuntimeException("Stub!");
    }
}
