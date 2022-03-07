package android.text.style;

import android.graphics.*;
import android.text.*;

public interface LineHeightSpan extends ParagraphStyle, WrapTogetherSpan
{
    void chooseHeight(final CharSequence p0, final int p1, final int p2, final int p3, final int p4, final Paint.FontMetricsInt p5);
    
    public interface WithDensity extends LineHeightSpan
    {
        void chooseHeight(final CharSequence p0, final int p1, final int p2, final int p3, final int p4, final Paint.FontMetricsInt p5, final TextPaint p6);
    }
}
