package android.graphics.drawable;

import android.content.res.*;
import android.graphics.*;
import org.xmlpull.v1.*;
import java.io.*;
import android.util.*;

public abstract class Drawable
{
    public Drawable() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void draw(final Canvas p0);
    
    public void setBounds(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBounds(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public final void copyBounds(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public final Rect copyBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public final Rect getBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public Rect getDirtyBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public void setChangingConfigurations(final int configs) {
        throw new RuntimeException("Stub!");
    }
    
    public int getChangingConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setDither(final boolean dither) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFilterBitmap(final boolean filter) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFilterBitmap() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setCallback(final Callback cb) {
        throw new RuntimeException("Stub!");
    }
    
    public Callback getCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void invalidateSelf() {
        throw new RuntimeException("Stub!");
    }
    
    public void scheduleSelf(final Runnable what, final long when) {
        throw new RuntimeException("Stub!");
    }
    
    public void unscheduleSelf(final Runnable what) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLayoutDirection() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean setLayoutDirection(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onLayoutDirectionChanged(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void setAlpha(final int p0);
    
    public int getAlpha() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void setColorFilter(final ColorFilter p0);
    
    public void setColorFilter(final int color, final PorterDuff.Mode mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTint(final int tintColor) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorFilter getColorFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public void clearColorFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHotspot(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    public void setHotspotBounds(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void getHotspotBounds(final Rect outRect) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isStateful() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setState(final int[] stateSet) {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getState() {
        throw new RuntimeException("Stub!");
    }
    
    public void jumpToCurrentState() {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getCurrent() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean setLevel(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getLevel() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setVisible(final boolean visible, final boolean restart) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAutoMirrored(final boolean mirrored) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAutoMirrored() {
        throw new RuntimeException("Stub!");
    }
    
    public void applyTheme(final Resources.Theme t) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canApplyTheme() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getOpacity();
    
    public static int resolveOpacity(final int op1, final int op2) {
        throw new RuntimeException("Stub!");
    }
    
    public Region getTransparentRegion() {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onStateChange(final int[] state) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onLevelChange(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onBoundsChange(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public int getIntrinsicWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIntrinsicHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMinimumWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMinimumHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getPadding(final Rect padding) {
        throw new RuntimeException("Stub!");
    }
    
    public void getOutline(final Outline outline) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable mutate() {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromStream(final InputStream is, final String srcName) {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromResourceStream(final Resources res, final TypedValue value, final InputStream is, final String srcName) {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromResourceStream(final Resources res, final TypedValue value, final InputStream is, final String srcName, final BitmapFactory.Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromXml(final Resources r, final XmlPullParser parser) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromXml(final Resources r, final XmlPullParser parser, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromXmlInner(final Resources r, final XmlPullParser parser, final AttributeSet attrs) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromXmlInner(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static Drawable createFromPath(final String pathName) {
        throw new RuntimeException("Stub!");
    }
    
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public ConstantState getConstantState() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class ConstantState
    {
        public ConstantState() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract Drawable newDrawable();
        
        public Drawable newDrawable(final Resources res) {
            throw new RuntimeException("Stub!");
        }
        
        public Drawable newDrawable(final Resources res, final Resources.Theme theme) {
            throw new RuntimeException("Stub!");
        }
        
        public abstract int getChangingConfigurations();
        
        public boolean canApplyTheme() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface Callback
    {
        void invalidateDrawable(final Drawable p0);
        
        void scheduleDrawable(final Drawable p0, final Runnable p1, final long p2);
        
        void unscheduleDrawable(final Drawable p0, final Runnable p1);
    }
}
