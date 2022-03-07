package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;
import android.graphics.*;

public class RippleDrawable extends LayerDrawable
{
    public static final int RADIUS_AUTO = -1;
    
    public RippleDrawable(final ColorStateList color, final Drawable content, final Drawable mask) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void jumpToCurrentState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getOpacity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onStateChange(final int[] stateSet) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onBoundsChange(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setVisible(final boolean visible, final boolean restart) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isStateful() {
        throw new RuntimeException("Stub!");
    }
    
    public void setColor(final ColorStateList color) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRadius(final int radius) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRadius() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setDrawableByLayerId(final int id, final Drawable drawable) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setPaddingMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void applyTheme(final Resources.Theme t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean canApplyTheme() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setHotspot(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setHotspotBounds(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void getHotspotBounds(final Rect outRect) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void getOutline(final Outline outline) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void invalidateSelf() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Rect getDirtyBounds() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ConstantState getConstantState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable mutate() {
        throw new RuntimeException("Stub!");
    }
}
