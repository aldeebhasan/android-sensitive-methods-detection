package android.widget;

import android.view.*;

public static class ExpandableListContextMenuInfo implements ContextMenu.ContextMenuInfo
{
    public long id;
    public long packedPosition;
    public View targetView;
    
    public ExpandableListContextMenuInfo(final View targetView, final long packedPosition, final long id) {
        throw new RuntimeException("Stub!");
    }
}
