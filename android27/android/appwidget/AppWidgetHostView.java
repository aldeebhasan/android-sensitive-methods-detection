package android.appwidget;

import android.content.*;
import android.util.*;
import android.os.*;
import java.util.concurrent.*;
import android.widget.*;
import android.graphics.*;
import android.view.*;

public class AppWidgetHostView extends FrameLayout
{
    public AppWidgetHostView(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AppWidgetHostView(final Context context, final int animationIn, final int animationOut) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public void setAppWidget(final int appWidgetId, final AppWidgetProviderInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public static Rect getDefaultPaddingForWidget(final Context context, final ComponentName component, final Rect padding) {
        throw new RuntimeException("Stub!");
    }
    
    public int getAppWidgetId() {
        throw new RuntimeException("Stub!");
    }
    
    public AppWidgetProviderInfo getAppWidgetInfo() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dispatchSaveInstanceState(final SparseArray<Parcelable> container) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dispatchRestoreInstanceState(final SparseArray<Parcelable> container) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateAppWidgetSize(final Bundle newOptions, final int minWidth, final int minHeight, final int maxWidth, final int maxHeight) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateAppWidgetOptions(final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public LayoutParams generateLayoutParams(final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExecutor(final Executor executor) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateAppWidget(final RemoteViews remoteViews) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean drawChild(final Canvas canvas, final View child, final long drawingTime) {
        throw new RuntimeException("Stub!");
    }
    
    protected void prepareView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    protected View getDefaultView() {
        throw new RuntimeException("Stub!");
    }
    
    protected View getErrorView() {
        throw new RuntimeException("Stub!");
    }
}
