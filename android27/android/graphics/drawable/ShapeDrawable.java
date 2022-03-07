package android.graphics.drawable;

import android.graphics.drawable.shapes.*;
import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;
import android.graphics.*;

public class ShapeDrawable extends Drawable
{
    public ShapeDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public ShapeDrawable(final Shape s) {
        throw new RuntimeException("Stub!");
    }
    
    public Shape getShape() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShape(final Shape s) {
        throw new RuntimeException("Stub!");
    }
    
    public void setShaderFactory(final ShaderFactory fact) {
        throw new RuntimeException("Stub!");
    }
    
    public ShaderFactory getShaderFactory() {
        throw new RuntimeException("Stub!");
    }
    
    public Paint getPaint() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPadding(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPadding(final Rect padding) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntrinsicWidth(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntrinsicHeight(final int height) {
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
    public boolean getPadding(final Rect padding) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onDraw(final Shape shape, final Canvas canvas, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas) {
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
    public void setTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setColorFilter(final ColorFilter colorFilter) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getOpacity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setDither(final boolean dither) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onBoundsChange(final Rect bounds) {
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
    
    protected boolean inflateTag(final String name, final Resources r, final XmlPullParser parser, final AttributeSet attrs) {
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
    public void getOutline(final Outline outline) {
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
    
    public abstract static class ShaderFactory
    {
        public ShaderFactory() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract Shader resize(final int p0, final int p1);
    }
}
