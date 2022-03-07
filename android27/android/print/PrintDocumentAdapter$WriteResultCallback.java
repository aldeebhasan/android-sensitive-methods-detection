package android.print;

public abstract static class WriteResultCallback
{
    WriteResultCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onWriteFinished(final PageRange[] pages) {
        throw new RuntimeException("Stub!");
    }
    
    public void onWriteFailed(final CharSequence error) {
        throw new RuntimeException("Stub!");
    }
    
    public void onWriteCancelled() {
        throw new RuntimeException("Stub!");
    }
}
