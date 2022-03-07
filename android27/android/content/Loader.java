package android.content;

import java.io.*;
import android.database.*;
import android.os.*;

public class Loader<D>
{
    public Loader(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void deliverResult(final D data) {
        throw new RuntimeException("Stub!");
    }
    
    public void deliverCancellation() {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerListener(final int id, final OnLoadCompleteListener<D> listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterListener(final OnLoadCompleteListener<D> listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerOnLoadCanceledListener(final OnLoadCanceledListener<D> listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterOnLoadCanceledListener(final OnLoadCanceledListener<D> listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isStarted() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAbandoned() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isReset() {
        throw new RuntimeException("Stub!");
    }
    
    public final void startLoading() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onStartLoading() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean cancelLoad() {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onCancelLoad() {
        throw new RuntimeException("Stub!");
    }
    
    public void forceLoad() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onForceLoad() {
        throw new RuntimeException("Stub!");
    }
    
    public void stopLoading() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onStopLoading() {
        throw new RuntimeException("Stub!");
    }
    
    public void abandon() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onAbandon() {
        throw new RuntimeException("Stub!");
    }
    
    public void reset() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onReset() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean takeContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public void commitContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public void rollbackContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public void onContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public String dataToString(final D data) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public final class ForceLoadContentObserver extends ContentObserver
    {
        public ForceLoadContentObserver() {
            super(null);
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean deliverSelfNotifications() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void onChange(final boolean selfChange) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnLoadCanceledListener<D>
    {
        void onLoadCanceled(final Loader<D> p0);
    }
    
    public interface OnLoadCompleteListener<D>
    {
        void onLoadComplete(final Loader<D> p0, final D p1);
    }
}
