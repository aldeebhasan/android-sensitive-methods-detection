package android.transition;

import android.content.*;
import android.util.*;
import android.view.*;
import android.animation.*;

public class Fade extends Visibility
{
    public static final int IN = 1;
    public static final int OUT = 2;
    
    public Fade() {
        throw new RuntimeException("Stub!");
    }
    
    public Fade(final int fadingMode) {
        throw new RuntimeException("Stub!");
    }
    
    public Fade(final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void captureStartValues(final TransitionValues transitionValues) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Animator onAppear(final ViewGroup sceneRoot, final View view, final TransitionValues startValues, final TransitionValues endValues) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Animator onDisappear(final ViewGroup sceneRoot, final View view, final TransitionValues startValues, final TransitionValues endValues) {
        throw new RuntimeException("Stub!");
    }
}
