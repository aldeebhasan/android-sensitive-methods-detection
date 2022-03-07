package android.inputmethodservice;

import android.content.*;
import java.util.*;
import android.content.res.*;
import android.graphics.drawable.*;

public class Keyboard
{
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    public static final int KEYCODE_ALT = -6;
    public static final int KEYCODE_CANCEL = -3;
    public static final int KEYCODE_DELETE = -5;
    public static final int KEYCODE_DONE = -4;
    public static final int KEYCODE_MODE_CHANGE = -2;
    public static final int KEYCODE_SHIFT = -1;
    
    public Keyboard(final Context context, final int xmlLayoutResId) {
        throw new RuntimeException("Stub!");
    }
    
    public Keyboard(final Context context, final int xmlLayoutResId, final int modeId, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Keyboard(final Context context, final int xmlLayoutResId, final int modeId) {
        throw new RuntimeException("Stub!");
    }
    
    public Keyboard(final Context context, final int layoutTemplateResId, final CharSequence characters, final int columns, final int horizontalPadding) {
        throw new RuntimeException("Stub!");
    }
    
    public List<Key> getKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Key> getModifierKeys() {
        throw new RuntimeException("Stub!");
    }
    
    protected int getHorizontalGap() {
        throw new RuntimeException("Stub!");
    }
    
    protected void setHorizontalGap(final int gap) {
        throw new RuntimeException("Stub!");
    }
    
    protected int getVerticalGap() {
        throw new RuntimeException("Stub!");
    }
    
    protected void setVerticalGap(final int gap) {
        throw new RuntimeException("Stub!");
    }
    
    protected int getKeyHeight() {
        throw new RuntimeException("Stub!");
    }
    
    protected void setKeyHeight(final int height) {
        throw new RuntimeException("Stub!");
    }
    
    protected int getKeyWidth() {
        throw new RuntimeException("Stub!");
    }
    
    protected void setKeyWidth(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMinWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setShifted(final boolean shiftState) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isShifted() {
        throw new RuntimeException("Stub!");
    }
    
    public int getShiftKeyIndex() {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getNearestKeys(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    protected Row createRowFromXml(final Resources res, final XmlResourceParser parser) {
        throw new RuntimeException("Stub!");
    }
    
    protected Key createKeyFromXml(final Resources res, final Row parent, final int x, final int y, final XmlResourceParser parser) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Row
    {
        public int defaultHeight;
        public int defaultHorizontalGap;
        public int defaultWidth;
        public int mode;
        public int rowEdgeFlags;
        public int verticalGap;
        
        public Row(final Keyboard parent) {
            throw new RuntimeException("Stub!");
        }
        
        public Row(final Resources res, final Keyboard parent, final XmlResourceParser parser) {
            throw new RuntimeException("Stub!");
        }
    }
    
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
}
