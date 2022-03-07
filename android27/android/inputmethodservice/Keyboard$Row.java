package android.inputmethodservice;

import android.content.res.*;

public static class Row
{
    public int defaultHeight;
    public int defaultHorizontalGap;
    public int defaultWidth;
    public int mode;
    public int rowEdgeFlags;
    public int verticalGap;
    
    public Row(final Keyboard parent) {
        throw new RuntimeException("Stub!");
    }
    
    public Row(final Resources res, final Keyboard parent, final XmlResourceParser parser) {
        throw new RuntimeException("Stub!");
    }
}
