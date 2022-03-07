package android.widget;

public interface OnScrollListener
{
    public static final int SCROLL_STATE_FLING = 2;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_TOUCH_SCROLL = 1;
    
    void onScrollStateChange(final NumberPicker p0, final int p1);
}
