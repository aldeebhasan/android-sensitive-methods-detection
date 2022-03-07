package android.widget;

public interface RemoteViewsFactory
{
    void onCreate();
    
    void onDataSetChanged();
    
    void onDestroy();
    
    int getCount();
    
    RemoteViews getViewAt(final int p0);
    
    RemoteViews getLoadingView();
    
    int getViewTypeCount();
    
    long getItemId(final int p0);
    
    boolean hasStableIds();
}
