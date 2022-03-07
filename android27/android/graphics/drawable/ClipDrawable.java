package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;
import android.graphics.*;

public class ClipDrawable extends DrawableWrapper
{
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    
    public ClipDrawable(final Drawable drawable, final int gravity, final int orientation) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void applyTheme(final Resources.Theme t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onLevelChange(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getOpacity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
}
