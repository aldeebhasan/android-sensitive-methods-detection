package android.text;

import android.graphics.*;

public abstract class Layout
{
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final int DIR_LEFT_TO_RIGHT = 1;
    public static final int DIR_RIGHT_TO_LEFT = -1;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    
    protected Layout(final CharSequence text, final TextPaint paint, final int width, final Alignment align, final float spacingMult, final float spacingAdd) {
        throw new RuntimeException("Stub!");
    }
    
    public static float getDesiredWidth(final CharSequence source, final TextPaint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public static float getDesiredWidth(final CharSequence source, final int start, final int end, final TextPaint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void draw(final Canvas c) {
        throw new RuntimeException("Stub!");
    }
    
    public void draw(final Canvas canvas, final Path highlight, final Paint highlightPaint, final int cursorOffsetVertical) {
        throw new RuntimeException("Stub!");
    }
    
    public final CharSequence getText() {
        throw new RuntimeException("Stub!");
    }
    
    public final TextPaint getPaint() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getEllipsizedWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public final void increaseWidthTo(final int wid) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public final Alignment getAlignment() {
        throw new RuntimeException("Stub!");
    }
    
    public final float getSpacingMultiplier() {
        throw new RuntimeException("Stub!");
    }
    
    public final float getSpacingAdd() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getLineCount();
    
    public int getLineBounds(final int line, final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getLineTop(final int p0);
    
    public abstract int getLineDescent(final int p0);
    
    public abstract int getLineStart(final int p0);
    
    public abstract int getParagraphDirection(final int p0);
    
    public abstract boolean getLineContainsTab(final int p0);
    
    public abstract Directions getLineDirections(final int p0);
    
    public abstract int getTopPadding();
    
    public abstract int getBottomPadding();
    
    public boolean isRtlCharAt(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public float getPrimaryHorizontal(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public float getSecondaryHorizontal(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public float getLineLeft(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public float getLineRight(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public float getLineMax(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public float getLineWidth(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLineForVertical(final int vertical) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLineForOffset(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOffsetForHorizontal(final int line, final float horiz) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getLineEnd(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLineVisibleEnd(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getLineBottom(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getLineBaseline(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getLineAscent(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOffsetToLeftOf(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOffsetToRightOf(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public void getCursorPath(final int point, final Path dest, final CharSequence editingBuffer) {
        throw new RuntimeException("Stub!");
    }
    
    public void getSelectionPath(final int start, final int end, final Path dest) {
        throw new RuntimeException("Stub!");
    }
    
    public final Alignment getParagraphAlignment(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getParagraphLeft(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getParagraphRight(final int line) {
        throw new RuntimeException("Stub!");
    }
    
    protected final boolean isSpanned() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getEllipsisStart(final int p0);
    
    public abstract int getEllipsisCount(final int p0);
    
    public static class Directions
    {
        Directions() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public enum Alignment
    {
        ALIGN_CENTER, 
        ALIGN_NORMAL, 
        ALIGN_OPPOSITE;
    }
}
