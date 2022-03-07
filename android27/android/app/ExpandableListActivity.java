package android.app;

import android.view.*;
import android.os.*;
import android.widget.*;

public class ExpandableListActivity extends Activity implements View.OnCreateContextMenuListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupCollapseListener, ExpandableListView.OnGroupExpandListener
{
    public ExpandableListActivity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onChildClick(final ExpandableListView parent, final View v, final int groupPosition, final int childPosition, final long id) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onGroupCollapse(final int groupPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onGroupExpand(final int groupPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onRestoreInstanceState(final Bundle state) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public void setListAdapter(final ExpandableListAdapter adapter) {
        throw new RuntimeException("Stub!");
    }
    
    public ExpandableListView getExpandableListView() {
        throw new RuntimeException("Stub!");
    }
    
    public ExpandableListAdapter getExpandableListAdapter() {
        throw new RuntimeException("Stub!");
    }
    
    public long getSelectedId() {
        throw new RuntimeException("Stub!");
    }
    
    public long getSelectedPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setSelectedChild(final int groupPosition, final int childPosition, final boolean shouldExpandGroup) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSelectedGroup(final int groupPosition) {
        throw new RuntimeException("Stub!");
    }
}
