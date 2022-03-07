package android.content;

import java.io.*;

public abstract class AsyncTaskLoader<D> extends Loader<D>
{
    public AsyncTaskLoader(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void setUpdateThrottle(final long delayMS) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onForceLoad() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onCancelLoad() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCanceled(final D data) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract D loadInBackground();
    
    protected D onLoadInBackground() {
        throw new RuntimeException("Stub!");
    }
    
    public void cancelLoadInBackground() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isLoadInBackgroundCanceled() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
