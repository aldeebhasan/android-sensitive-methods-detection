package android.app;

import android.os.*;
import android.content.*;

public interface LoaderCallbacks<D>
{
    Loader<D> onCreateLoader(final int p0, final Bundle p1);
    
    void onLoadFinished(final Loader<D> p0, final D p1);
    
    void onLoaderReset(final Loader<D> p0);
}
