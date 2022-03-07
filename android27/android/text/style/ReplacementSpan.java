package android.text.style;

import android.graphics.*;
import android.text.*;

public abstract class ReplacementSpan extends MetricAffectingSpan
{
    public ReplacementSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getSize(final Paint p0, final CharSequence p1, final int p2, final int p3, final Paint.FontMetricsInt p4);
    
    public abstract void draw(final Canvas p0, final CharSequence p1, final int p2, final int p3, final float p4, final int p5, final int p6, final int p7, final Paint p8);
    
    @Override
    public void updateMeasureState(final TextPaint p) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateDrawState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
}
