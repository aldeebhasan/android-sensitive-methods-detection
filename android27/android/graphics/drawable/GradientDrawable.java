package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;
import android.graphics.*;

public class GradientDrawable extends Drawable
{
    public static final int LINE = 2;
    public static final int LINEAR_GRADIENT = 0;
    public static final int OVAL = 1;
    public static final int RADIAL_GRADIENT = 1;
    public static final int RECTANGLE = 0;
    public static final int RING = 3;
    public static final int SWEEP_GRADIENT = 2;
    
    public GradientDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public GradientDrawable(final Orientation orientation, final int[] colors) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean getPadding(final Rect padding) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCornerRadii(final float[] radii) {
        throw new RuntimeException("Stub!");
    }
    
    public float[] getCornerRadii() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCornerRadius(final float radius) {
        throw new RuntimeException("Stub!");
    }
    
    public float getCornerRadius() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStroke(final int width, final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public void setStroke(final int width, final ColorStateList colorStateList) {
        throw new RuntimeException("Stub!");
    }
    
    public void setStroke(final int width, final int color, final float dashWidth, final float dashGap) {
        throw new RuntimeException("Stub!");
    }
    
    public void setStroke(final int width, final ColorStateList colorStateList, final float dashWidth, final float dashGap) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSize(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void setShape(final int shape) {
        throw new RuntimeException("Stub!");
    }
    
    public int getShape() {
        throw new RuntimeException("Stub!");
    }
    
    public void setGradientType(final int gradient) {
        throw new RuntimeException("Stub!");
    }
    
    public int getGradientType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setGradientCenter(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    public float getGradientCenterX() {
        throw new RuntimeException("Stub!");
    }
    
    public float getGradientCenterY() {
        throw new RuntimeException("Stub!");
    }
    
    public void setGradientRadius(final float gradientRadius) {
        throw new RuntimeException("Stub!");
    }
    
    public float getGradientRadius() {
        throw new RuntimeException("Stub!");
    }
    
    public void setUseLevel(final boolean useLevel) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getUseLevel() {
        throw new RuntimeException("Stub!");
    }
    
    public Orientation getOrientation() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOrientation(final Orientation orientation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setColors(final int[] colors) {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getColors() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public void setColor(final int argb) {
        throw new RuntimeException("Stub!");
    }
    
    public void setColor(final ColorStateList colorStateList) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getColor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onStateChange(final int[] stateSet) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isStateful() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getChangingConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setAlpha(final int alpha) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getAlpha() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setDither(final boolean dither) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ColorFilter getColorFilter() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setColorFilter(final ColorFilter colorFilter) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getOpacity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onBoundsChange(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onLevelChange(final int level) {
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
    public boolean canApplyTheme() {
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
    public ConstantState getConstantState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void getOutline(final Outline outline) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable mutate() {
        throw new RuntimeException("Stub!");
    }
    
    public enum Orientation
    {
        BL_TR, 
        BOTTOM_TOP, 
        BR_TL, 
        LEFT_RIGHT, 
        RIGHT_LEFT, 
        TL_BR, 
        TOP_BOTTOM, 
        TR_BL;
    }
}
