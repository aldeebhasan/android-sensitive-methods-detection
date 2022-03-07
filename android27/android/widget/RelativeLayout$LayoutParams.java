package android.widget;

import android.view.*;
import android.content.*;
import android.util.*;

public static class LayoutParams extends MarginLayoutParams
{
    @ViewDebug.ExportedProperty(category = "layout")
    public boolean alignWithParent;
    
    public LayoutParams(final Context c, final AttributeSet attrs) {
        super((ViewGroup.LayoutParams)null);
        throw new RuntimeException("Stub!");
    }
    
    public LayoutParams(final int w, final int h) {
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
    
    public String debug(final String output) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRule(final int verb) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRule(final int verb, final int subject) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeRule(final int verb) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRule(final int verb) {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getRules() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void resolveLayoutDirection(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
}
