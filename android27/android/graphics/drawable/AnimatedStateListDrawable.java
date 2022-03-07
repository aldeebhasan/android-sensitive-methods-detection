package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;

public class AnimatedStateListDrawable extends StateListDrawable
{
    public AnimatedStateListDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setVisible(final boolean visible, final boolean restart) {
        throw new RuntimeException("Stub!");
    }
    
    public void addState(final int[] stateSet, final Drawable drawable, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends android.graphics.drawable.Drawable> void addTransition(final int fromId, final int toId, final T transition, final boolean reversible) {
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
    public void jumpToCurrentState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void inflate(final Resources r, final XmlPullParser parser, final AttributeSet attrs, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void applyTheme(final Resources.Theme theme) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable mutate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setConstantState(final DrawableContainerState state) {
        throw new RuntimeException("Stub!");
    }
}
