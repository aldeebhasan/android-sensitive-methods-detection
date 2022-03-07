package android.widget;

import android.view.*;
import android.content.*;
import android.util.*;

public static class LayoutParams extends MarginLayoutParams
{
    public static final int UNSPECIFIED_GRAVITY = -1;
    public int gravity;
    
    public LayoutParams(final Context c, final AttributeSet attrs) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int width, final int height) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int width, final int height, final int gravity) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final ViewGroup.LayoutParams source) {
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
}
