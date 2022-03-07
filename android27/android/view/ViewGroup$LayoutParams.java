package android.view;

import android.view.animation.*;
import android.content.*;
import android.util.*;
import android.content.res.*;

public static class LayoutParams
{
    @Deprecated
    public static final int FILL_PARENT = -1;
    public static final int MATCH_PARENT = -1;
    public static final int WRAP_CONTENT = -2;
    @ViewDebug.ExportedProperty(category = "layout", mapping = { @ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"), @ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT") })
    public int height;
    public LayoutAnimationController.AnimationParameters layoutAnimationParameters;
    @ViewDebug.ExportedProperty(category = "layout", mapping = { @ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"), @ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT") })
    public int width;
    
    public LayoutParams(final Context c, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final LayoutParams source) {
        throw new RuntimeException("Stub!");
    }
    
    protected void setBaseAttributes(final TypedArray a, final int widthAttr, final int heightAttr) {
        throw new RuntimeException("Stub!");
    }
    
    public void resolveLayoutDirection(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
}
