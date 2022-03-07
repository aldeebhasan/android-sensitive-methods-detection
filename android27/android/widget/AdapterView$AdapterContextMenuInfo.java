package android.widget;

import android.view.*;

public static class AdapterContextMenuInfo implements ContextMenu.ContextMenuInfo
{
    public long id;
    public int position;
    public View targetView;
    
    public AdapterContextMenuInfo(final View targetView, final int position, final long id) {
        throw new RuntimeException("Stub!");
    }
}
