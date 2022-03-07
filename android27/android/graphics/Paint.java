package android.graphics;

import java.util.*;
import android.os.*;

public class Paint
{
    public static final int ANTI_ALIAS_FLAG = 1;
    public static final int DEV_KERN_TEXT_FLAG = 256;
    public static final int DITHER_FLAG = 4;
    public static final int EMBEDDED_BITMAP_TEXT_FLAG = 1024;
    public static final int FAKE_BOLD_TEXT_FLAG = 32;
    public static final int FILTER_BITMAP_FLAG = 2;
    public static final int HINTING_OFF = 0;
    public static final int HINTING_ON = 1;
    public static final int LINEAR_TEXT_FLAG = 64;
    public static final int STRIKE_THRU_TEXT_FLAG = 16;
    public static final int SUBPIXEL_TEXT_FLAG = 128;
    public static final int UNDERLINE_TEXT_FLAG = 8;
    
    public Paint() {
        throw new RuntimeException("Stub!");
    }
    
    public Paint(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Paint(final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void reset() {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final Paint src) {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFlags(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHinting() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHinting(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isAntiAlias() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAntiAlias(final boolean aa) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isDither() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDither(final boolean dither) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isLinearText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLinearText(final boolean linearText) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isSubpixelText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSubpixelText(final boolean subpixelText) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isUnderlineText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setUnderlineText(final boolean underlineText) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isStrikeThruText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrikeThruText(final boolean strikeThruText) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isFakeBoldText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFakeBoldText(final boolean fakeBoldText) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isFilterBitmap() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFilterBitmap(final boolean filter) {
        throw new RuntimeException("Stub!");
    }
    
    public Style getStyle() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStyle(final Style style) {
        throw new RuntimeException("Stub!");
    }
    
    public int getColor() {
        throw new RuntimeException("Stub!");
    }
    
    public void setColor(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public int getAlpha() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAlpha(final int a) {
        throw new RuntimeException("Stub!");
    }
    
    public void setARGB(final int a, final int r, final int g, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    public float getStrokeWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrokeWidth(final float width) {
        throw new RuntimeException("Stub!");
    }
    
    public float getStrokeMiter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrokeMiter(final float miter) {
        throw new RuntimeException("Stub!");
    }
    
    public Cap getStrokeCap() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrokeCap(final Cap cap) {
        throw new RuntimeException("Stub!");
    }
    
    public Join getStrokeJoin() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrokeJoin(final Join join) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getFillPath(final Path src, final Path dst) {
        throw new RuntimeException("Stub!");
    }
    
    public Shader getShader() {
        throw new RuntimeException("Stub!");
    }
    
    public Shader setShader(final Shader shader) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorFilter getColorFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public ColorFilter setColorFilter(final ColorFilter filter) {
        throw new RuntimeException("Stub!");
    }
    
    public Xfermode getXfermode() {
        throw new RuntimeException("Stub!");
    }
    
    public Xfermode setXfermode(final Xfermode xfermode) {
        throw new RuntimeException("Stub!");
    }
    
    public PathEffect getPathEffect() {
        throw new RuntimeException("Stub!");
    }
    
    public PathEffect setPathEffect(final PathEffect effect) {
        throw new RuntimeException("Stub!");
    }
    
    public MaskFilter getMaskFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public MaskFilter setMaskFilter(final MaskFilter maskfilter) {
        throw new RuntimeException("Stub!");
    }
    
    public Typeface getTypeface() {
        throw new RuntimeException("Stub!");
    }
    
    public Typeface setTypeface(final Typeface typeface) {
        throw new RuntimeException("Stub!");
    }
    
    public void setShadowLayer(final float radius, final float dx, final float dy, final int shadowColor) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearShadowLayer() {
        throw new RuntimeException("Stub!");
    }
    
    public Align getTextAlign() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextAlign(final Align align) {
        throw new RuntimeException("Stub!");
    }
    
    public Locale getTextLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public LocaleList getTextLocales() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextLocale(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextLocales(final LocaleList locales) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isElegantTextHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public void setElegantTextHeight(final boolean elegant) {
        throw new RuntimeException("Stub!");
    }
    
    public float getTextSize() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextSize(final float textSize) {
        throw new RuntimeException("Stub!");
    }
    
    public float getTextScaleX() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextScaleX(final float scaleX) {
        throw new RuntimeException("Stub!");
    }
    
    public float getTextSkewX() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextSkewX(final float skewX) {
        throw new RuntimeException("Stub!");
    }
    
    public float getLetterSpacing() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLetterSpacing(final float letterSpacing) {
        throw new RuntimeException("Stub!");
    }
    
    public String getFontFeatureSettings() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFontFeatureSettings(final String settings) {
        throw new RuntimeException("Stub!");
    }
    
    public String getFontVariationSettings() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setFontVariationSettings(final String fontVariationSettings) {
        throw new RuntimeException("Stub!");
    }
    
    public float ascent() {
        throw new RuntimeException("Stub!");
    }
    
    public float descent() {
        throw new RuntimeException("Stub!");
    }
    
    public float getFontMetrics(final FontMetrics metrics) {
        throw new RuntimeException("Stub!");
    }
    
    public FontMetrics getFontMetrics() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFontMetricsInt(final FontMetricsInt fmi) {
        throw new RuntimeException("Stub!");
    }
    
    public FontMetricsInt getFontMetricsInt() {
        throw new RuntimeException("Stub!");
    }
    
    public float getFontSpacing() {
        throw new RuntimeException("Stub!");
    }
    
    public float measureText(final char[] text, final int index, final int count) {
        throw new RuntimeException("Stub!");
    }
    
    public float measureText(final String text, final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    public float measureText(final String text) {
        throw new RuntimeException("Stub!");
    }
    
    public float measureText(final CharSequence text, final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    public int breakText(final char[] text, final int index, final int count, final float maxWidth, final float[] measuredWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public int breakText(final CharSequence text, final int start, final int end, final boolean measureForwards, final float maxWidth, final float[] measuredWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public int breakText(final String text, final boolean measureForwards, final float maxWidth, final float[] measuredWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTextWidths(final char[] text, final int index, final int count, final float[] widths) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTextWidths(final CharSequence text, final int start, final int end, final float[] widths) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTextWidths(final String text, final int start, final int end, final float[] widths) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTextWidths(final String text, final float[] widths) {
        throw new RuntimeException("Stub!");
    }
    
    public void getTextPath(final char[] text, final int index, final int count, final float x, final float y, final Path path) {
        throw new RuntimeException("Stub!");
    }
    
    public void getTextPath(final String text, final int start, final int end, final float x, final float y, final Path path) {
        throw new RuntimeException("Stub!");
    }
    
    public void getTextBounds(final String text, final int start, final int end, final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public void getTextBounds(final char[] text, final int index, final int count, final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasGlyph(final String string) {
        throw new RuntimeException("Stub!");
    }
    
    public float getRunAdvance(final char[] text, final int start, final int end, final int contextStart, final int contextEnd, final boolean isRtl, final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public float getRunAdvance(final CharSequence text, final int start, final int end, final int contextStart, final int contextEnd, final boolean isRtl, final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOffsetForAdvance(final char[] text, final int start, final int end, final int contextStart, final int contextEnd, final boolean isRtl, final float advance) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOffsetForAdvance(final CharSequence text, final int start, final int end, final int contextStart, final int contextEnd, final boolean isRtl, final float advance) {
        throw new RuntimeException("Stub!");
    }
    
    public enum Style
    {
        FILL, 
        FILL_AND_STROKE, 
        STROKE;
    }
    
    public enum Cap
    {
        BUTT, 
        ROUND, 
        SQUARE;
    }
    
    public enum Join
    {
        BEVEL, 
        MITER, 
        ROUND;
    }
    
    public enum Align
    {
        CENTER, 
        LEFT, 
        RIGHT;
    }
    
    public static class FontMetrics
    {
        public float ascent;
        public float bottom;
        public float descent;
        public float leading;
        public float top;
        
        public FontMetrics() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class FontMetricsInt
    {
        public int ascent;
        public int bottom;
        public int descent;
        public int leading;
        public int top;
        
        public FontMetricsInt() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
}
