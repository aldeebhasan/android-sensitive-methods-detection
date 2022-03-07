package android.widget;

import android.view.*;

public interface MultiChoiceModeListener extends ActionMode.Callback
{
    void onItemCheckedStateChanged(final ActionMode p0, final int p1, final long p2, final boolean p3);
}
