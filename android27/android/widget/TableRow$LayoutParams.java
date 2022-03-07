package android.widget;

import android.content.*;
import android.util.*;
import android.view.*;
import android.content.res.*;

public static class LayoutParams extends LinearLayout.LayoutParams
{
    @ViewDebug.ExportedProperty(category = "layout")
    public int column;
    @ViewDebug.ExportedProperty(category = "layout")
    public int span;
    
    public LayoutParams(final Context c, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int w, final int h) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int w, final int h, final float initWeight) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams() {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int column) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final ViewGroup.LayoutParams p) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final MarginLayoutParams source) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setBaseAttributes(final TypedArray a, final int widthAttr, final int heightAttr) {
        throw new RuntimeException("Stub!");
    }
}
