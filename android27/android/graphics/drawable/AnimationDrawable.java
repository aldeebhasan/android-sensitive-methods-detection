package android.graphics.drawable;

import android.content.res.*;
import android.util.*;
import org.xmlpull.v1.*;
import java.io.*;

public class AnimationDrawable extends DrawableContainer implements Runnable, Animatable
{
    public AnimationDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setVisible(final boolean visible, final boolean restart) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void start() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void stop() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isRunning() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void run() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void unscheduleSelf(final Runnable what) {
        throw new RuntimeException("Stub!");
    }
    
    public int getNumberOfFrames() {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getFrame(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDuration(final int i) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOneShot() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOneShot(final boolean oneShot) {
        throw new RuntimeException("Stub!");
    }
    
    public void addFrame(final Drawable frame, final int duration) {
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
    protected void setConstantState(final DrawableContainerState state) {
        throw new RuntimeException("Stub!");
    }
}
