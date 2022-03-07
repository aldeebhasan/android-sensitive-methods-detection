package android.text.style;

import android.text.*;

public abstract class MetricAffectingSpan extends CharacterStyle implements UpdateLayout
{
    public MetricAffectingSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void updateMeasureState(final TextPaint p0);
    
    @Override
    public MetricAffectingSpan getUnderlying() {
        throw new RuntimeException("Stub!");
    }
}
