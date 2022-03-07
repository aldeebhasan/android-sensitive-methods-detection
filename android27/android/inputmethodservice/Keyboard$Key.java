package android.inputmethodservice;

import android.graphics.drawable.*;
import android.content.res.*;

public static class Key
{
    public int[] codes;
    public int edgeFlags;
    public int gap;
    public int height;
    public Drawable icon;
    public Drawable iconPreview;
    public CharSequence label;
    public boolean modifier;
    public boolean on;
    public CharSequence popupCharacters;
    public int popupResId;
    public boolean pressed;
    public boolean repeatable;
    public boolean sticky;
    public CharSequence text;
    public int width;
    public int x;
    public int y;
    
    public Key(final Row parent) {
        this.codes = null;
        throw new RuntimeException("Stub!");
    }
    
    public Key(final Resources res, final Row parent, final int x, final int y, final XmlResourceParser parser) {
        this.codes = null;
        throw new RuntimeException("Stub!");
    }
    
    public void onPressed() {
        throw new RuntimeException("Stub!");
    }
    
    public void onReleased(final boolean inside) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInside(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public int squaredDistanceFrom(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getCurrentDrawableState() {
        throw new RuntimeException("Stub!");
    }
}
