package android.widget;

public interface ListAdapter extends Adapter
{
    boolean areAllItemsEnabled();
    
    boolean isEnabled(final int p0);
}
