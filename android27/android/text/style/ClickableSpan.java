package android.text.style;

import android.view.*;
import android.text.*;

public abstract class ClickableSpan extends CharacterStyle implements UpdateAppearance
{
    public ClickableSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onClick(final View p0);
    
    @Override
    public void updateDrawState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
}
