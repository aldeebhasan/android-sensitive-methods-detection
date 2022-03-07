package android.text;

import android.graphics.*;

public class BoringLayout extends Layout implements TextUtils.EllipsizeCallback
{
    public BoringLayout(final CharSequence source, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final Metrics metrics, final boolean includepad) {
        super(null, null, 0, null, 0.0f, 0.0f);
        throw new RuntimeException("Stub!");
    }
    
    public BoringLayout(final CharSequence source, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final Metrics metrics, final boolean includepad, final TextUtils.TruncateAt ellipsize, final int ellipsizedWidth) {
        super(null, null, 0, null, 0.0f, 0.0f);
        throw new RuntimeException("Stub!");
    }
    
    public static BoringLayout make(final CharSequence source, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final Metrics metrics, final boolean includepad) {
        throw new RuntimeException("Stub!");
    }
    
    public static BoringLayout make(final CharSequence source, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final Metrics metrics, final boolean includepad, final TextUtils.TruncateAt ellipsize, final int ellipsizedWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public BoringLayout replaceOrMake(final CharSequence source, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final Metrics metrics, final boolean includepad) {
        throw new RuntimeException("Stub!");
    }
    
    public BoringLayout replaceOrMake(final CharSequence source, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final Metrics metrics, final boolean includepad, final TextUtils.TruncateAt ellipsize, final int ellipsizedWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public static Metrics isBoring(final CharSequence text, final TextPaint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public static Metrics isBoring(final CharSequence text, final TextPaint paint, final Metrics metrics) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLineCount() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLineTop(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLineDescent(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLineStart(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getParagraphDirection(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean getLineContainsTab(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public float getLineMax(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public float getLineWidth(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final Directions getLineDirections(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getTopPadding() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getBottomPadding() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getEllipsisCount(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getEllipsisStart(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getEllipsizedWidth() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas c, final Path highlight, final Paint highlightpaint, final int cursorOffset) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void ellipsized(final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Metrics extends Paint.FontMetricsInt
    {
        public int width;
        
        public Metrics() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
}
