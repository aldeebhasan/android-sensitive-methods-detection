package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;

public class StateListDrawable extends DrawableContainer
{
    public StateListDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public void addState(final int[] stateSet, final Drawable drawable) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isStateful() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onStateChange(final int[] stateSet) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable mutate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void applyTheme(final Resources.Theme theme) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setConstantState(final DrawableContainerState state) {
        throw new RuntimeException("Stub!");
    }
}
