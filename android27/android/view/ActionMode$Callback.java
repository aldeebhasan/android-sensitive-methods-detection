package android.view;

public interface Callback
{
    boolean onCreateActionMode(final ActionMode p0, final Menu p1);
    
    boolean onPrepareActionMode(final ActionMode p0, final Menu p1);
    
    boolean onActionItemClicked(final ActionMode p0, final MenuItem p1);
    
    void onDestroyActionMode(final ActionMode p0);
}
