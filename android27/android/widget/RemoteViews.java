package android.widget;

import android.net.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import java.lang.annotation.*;

public class RemoteViews implements Parcelable, LayoutInflater.Filter
{
    public static final Creator<RemoteViews> CREATOR;
    
    public RemoteViews(final String packageName, final int layoutId) {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteViews(final RemoteViews landscape, final RemoteViews portrait) {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteViews(final Parcel parcel) {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteViews clone() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLayoutId() {
        throw new RuntimeException("Stub!");
    }
    
    public void addView(final int viewId, final RemoteViews nestedView) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeAllViews(final int viewId) {
        throw new RuntimeException("Stub!");
    }
    
    public void showNext(final int viewId) {
        throw new RuntimeException("Stub!");
    }
    
    public void showPrevious(final int viewId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDisplayedChild(final int viewId, final int childIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewVisibility(final int viewId, final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextViewText(final int viewId, final CharSequence text) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextViewTextSize(final int viewId, final int units, final float size) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextViewCompoundDrawables(final int viewId, final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextViewCompoundDrawablesRelative(final int viewId, final int start, final int top, final int end, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setImageViewResource(final int viewId, final int srcId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setImageViewUri(final int viewId, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public void setImageViewBitmap(final int viewId, final Bitmap bitmap) {
        throw new RuntimeException("Stub!");
    }
    
    public void setImageViewIcon(final int viewId, final Icon icon) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEmptyView(final int viewId, final int emptyViewId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setChronometer(final int viewId, final long base, final String format, final boolean started) {
        throw new RuntimeException("Stub!");
    }
    
    public void setChronometerCountDown(final int viewId, final boolean isCountDown) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressBar(final int viewId, final int max, final int progress, final boolean indeterminate) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnClickPendingIntent(final int viewId, final PendingIntent pendingIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPendingIntentTemplate(final int viewId, final PendingIntent pendingIntentTemplate) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnClickFillInIntent(final int viewId, final Intent fillInIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextColor(final int viewId, final int color) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setRemoteAdapter(final int appWidgetId, final int viewId, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRemoteAdapter(final int viewId, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setScrollPosition(final int viewId, final int position) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRelativeScrollPosition(final int viewId, final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewPadding(final int viewId, final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBoolean(final int viewId, final String methodName, final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setByte(final int viewId, final String methodName, final byte value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setShort(final int viewId, final String methodName, final short value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInt(final int viewId, final String methodName, final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLong(final int viewId, final String methodName, final long value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFloat(final int viewId, final String methodName, final float value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDouble(final int viewId, final String methodName, final double value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setChar(final int viewId, final String methodName, final char value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setString(final int viewId, final String methodName, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCharSequence(final int viewId, final String methodName, final CharSequence value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUri(final int viewId, final String methodName, final Uri value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBitmap(final int viewId, final String methodName, final Bitmap value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBundle(final int viewId, final String methodName, final Bundle value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntent(final int viewId, final String methodName, final Intent value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIcon(final int viewId, final String methodName, final Icon value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentDescription(final int viewId, final CharSequence contentDescription) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAccessibilityTraversalBefore(final int viewId, final int nextId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAccessibilityTraversalAfter(final int viewId, final int nextId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLabelFor(final int viewId, final int labeledId) {
        throw new RuntimeException("Stub!");
    }
    
    public View apply(final Context context, final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    public void reapply(final Context context, final View v) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onLoadClass(final Class clazz) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class ActionException extends RuntimeException
    {
        public ActionException(final Exception ex) {
            throw new RuntimeException("Stub!");
        }
        
        public ActionException(final String message) {
            throw new RuntimeException("Stub!");
        }
    }
    
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RemoteView {
    }
}
