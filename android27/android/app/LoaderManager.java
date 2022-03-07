package android.app;

import android.os.*;
import android.content.*;
import java.io.*;

public abstract class LoaderManager
{
    public LoaderManager() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract <D> Loader<D> initLoader(final int p0, final Bundle p1, final LoaderCallbacks<D> p2);
    
    public abstract <D> Loader<D> restartLoader(final int p0, final Bundle p1, final LoaderCallbacks<D> p2);
    
    public abstract void destroyLoader(final int p0);
    
    public abstract <D> Loader<D> getLoader(final int p0);
    
    public abstract void dump(final String p0, final FileDescriptor p1, final PrintWriter p2, final String[] p3);
    
    public static void enableDebugLogging(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public interface LoaderCallbacks<D>
    {
        Loader<D> onCreateLoader(final int p0, final Bundle p1);
        
        void onLoadFinished(final Loader<D> p0, final D p1);
        
        void onLoaderReset(final Loader<D> p0);
    }
}
