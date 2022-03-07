package android.widget;

import android.view.*;
import android.content.*;
import android.util.*;

public static class LayoutParams extends MarginLayoutParams
{
    @ViewDebug.ExportedProperty(category = "layout", mapping = { @ViewDebug.IntToString(from = -1, to = "NONE"), @ViewDebug.IntToString(from = 0, to = "NONE"), @ViewDebug.IntToString(from = 48, to = "TOP"), @ViewDebug.IntToString(from = 80, to = "BOTTOM"), @ViewDebug.IntToString(from = 3, to = "LEFT"), @ViewDebug.IntToString(from = 5, to = "RIGHT"), @ViewDebug.IntToString(from = 8388611, to = "START"), @ViewDebug.IntToString(from = 8388613, to = "END"), @ViewDebug.IntToString(from = 16, to = "CENTER_VERTICAL"), @ViewDebug.IntToString(from = 112, to = "FILL_VERTICAL"), @ViewDebug.IntToString(from = 1, to = "CENTER_HORIZONTAL"), @ViewDebug.IntToString(from = 7, to = "FILL_HORIZONTAL"), @ViewDebug.IntToString(from = 17, to = "CENTER"), @ViewDebug.IntToString(from = 119, to = "FILL") })
    public int gravity;
    @ViewDebug.ExportedProperty(category = "layout")
    public float weight;
    
    public LayoutParams(final Context c, final AttributeSet attrs) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int width, final int height) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int width, final int height, final float weight) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final ViewGroup.LayoutParams p) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final MarginLayoutParams source) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final LayoutParams source) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public String debug(final String output) {
        throw new RuntimeException("Stub!");
    }
}
