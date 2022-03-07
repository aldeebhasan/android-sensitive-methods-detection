package android.text;

import android.graphics.*;

public class TextPaint extends Paint
{
    public int baselineShift;
    public int bgColor;
    public float density;
    public int[] drawableState;
    public int linkColor;
    
    public TextPaint() {
        this.drawableState = null;
        throw new RuntimeException("Stub!");
    }
    
    public TextPaint(final int flags) {
        this.drawableState = null;
        throw new RuntimeException("Stub!");
    }
    
    public TextPaint(final Paint p) {
        this.drawableState = null;
        throw new RuntimeException("Stub!");
    }
    
    public void set(final TextPaint tp) {
        throw new RuntimeException("Stub!");
    }
}
