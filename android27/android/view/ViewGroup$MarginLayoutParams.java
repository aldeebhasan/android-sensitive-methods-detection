package android.view;

import android.content.*;
import android.util.*;

public static class MarginLayoutParams extends LayoutParams
{
    @ViewDebug.ExportedProperty(category = "layout")
    public int bottomMargin;
    @ViewDebug.ExportedProperty(category = "layout")
    public int leftMargin;
    @ViewDebug.ExportedProperty(category = "layout")
    public int rightMargin;
    @ViewDebug.ExportedProperty(category = "layout")
    public int topMargin;
    
    public MarginLayoutParams(final Context c, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MarginLayoutParams(final int width, final int height) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MarginLayoutParams(final MarginLayoutParams source) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MarginLayoutParams(final LayoutParams source) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void setMargins(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMarginStart(final int start) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMarginStart() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMarginEnd(final int end) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMarginEnd() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isMarginRelative() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLayoutDirection(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLayoutDirection() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void resolveLayoutDirection(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
}
