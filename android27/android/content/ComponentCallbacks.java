package android.content;

import android.content.res.*;

public interface ComponentCallbacks
{
    void onConfigurationChanged(final Configuration p0);
    
    void onLowMemory();
}
