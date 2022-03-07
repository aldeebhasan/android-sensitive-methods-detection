package android.widget;

public interface OnScrollListener
{
    public static final int SCROLL_STATE_FLING = 2;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_TOUCH_SCROLL = 1;
    
    void onScrollStateChanged(final AbsListView p0, final int p1);
    
    void onScroll(final AbsListView p0, final int p1, final int p2, final int p3);
}
