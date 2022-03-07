package android.widget;

import android.view.*;
import android.content.*;
import android.util.*;
import android.content.res.*;

public static class LayoutParams extends MarginLayoutParams
{
    public Spec columnSpec;
    public Spec rowSpec;
    
    public LayoutParams(final Spec rowSpec, final Spec columnSpec) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams() {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final ViewGroup.LayoutParams params) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final MarginLayoutParams params) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final LayoutParams source) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final Context context, final AttributeSet attrs) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public void setGravity(final int gravity) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setBaseAttributes(final TypedArray attributes, final int widthAttr, final int heightAttr) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
}
