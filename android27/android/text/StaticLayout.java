package android.text;

public class StaticLayout extends Layout
{
    public StaticLayout(final CharSequence source, final TextPaint paint, final int width, final Alignment align, final float spacingmult, final float spacingadd, final boolean includepad) {
        super(null, null, 0, null, 0.0f, 0.0f);
        throw new RuntimeException("Stub!");
    }
    
    public StaticLayout(final CharSequence source, final int bufstart, final int bufend, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final boolean includepad) {
        super(null, null, 0, null, 0.0f, 0.0f);
        throw new RuntimeException("Stub!");
    }
    
    public StaticLayout(final CharSequence source, final int bufstart, final int bufend, final TextPaint paint, final int outerwidth, final Alignment align, final float spacingmult, final float spacingadd, final boolean includepad, final TextUtils.TruncateAt ellipsize, final int ellipsizedWidth) {
        super(null, null, 0, null, 0.0f, 0.0f);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLineForVertical(final int vertical) {
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
    
    public static final class Builder
    {
        Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public static Builder obtain(final CharSequence source, final int start, final int end, final TextPaint paint, final int width) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setText(final CharSequence source) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAlignment(final Alignment alignment) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setTextDirection(final TextDirectionHeuristic textDir) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setLineSpacing(final float spacingAdd, final float spacingMult) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIncludePad(final boolean includePad) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setEllipsizedWidth(final int ellipsizedWidth) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setEllipsize(final TextUtils.TruncateAt ellipsize) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setMaxLines(final int maxLines) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setBreakStrategy(final int breakStrategy) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setHyphenationFrequency(final int hyphenationFrequency) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIndents(final int[] leftIndents, final int[] rightIndents) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setJustificationMode(final int justificationMode) {
            throw new RuntimeException("Stub!");
        }
        
        public StaticLayout build() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
    }
}
