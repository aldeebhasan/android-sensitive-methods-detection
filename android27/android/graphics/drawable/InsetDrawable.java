package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;
import android.graphics.*;

public class InsetDrawable extends DrawableWrapper
{
    public InsetDrawable(final Drawable drawable, final int inset) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public InsetDrawable(final Drawable drawable, final float inset) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public InsetDrawable(final Drawable drawable, final int insetLeft, final int insetTop, final int insetRight, final int insetBottom) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public InsetDrawable(final Drawable drawable, final float insetLeftFraction, final float insetTopFraction, final float insetRightFraction, final float insetBottomFraction) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void applyTheme(final Resources.Theme t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean getPadding(final Rect padding) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getOpacity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onBoundsChange(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getIntrinsicWidth() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getIntrinsicHeight() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void getOutline(final Outline outline) {
        throw new RuntimeException("Stub!");
    }
}
