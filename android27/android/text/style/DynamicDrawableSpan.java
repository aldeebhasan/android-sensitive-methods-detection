package android.text.style;

import android.graphics.drawable.*;
import android.graphics.*;

public abstract class DynamicDrawableSpan extends ReplacementSpan
{
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    protected final int mVerticalAlignment;
    
    public DynamicDrawableSpan() {
        throw new RuntimeException("Stub!");
    }
    
    protected DynamicDrawableSpan(final int verticalAlignment) {
        throw new RuntimeException("Stub!");
    }
    
    public int getVerticalAlignment() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Drawable getDrawable();
    
    @Override
    public int getSize(final Paint paint, final CharSequence text, final int start, final int end, final Paint.FontMetricsInt fm) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas, final CharSequence text, final int start, final int end, final float x, final int top, final int y, final int bottom, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
}
