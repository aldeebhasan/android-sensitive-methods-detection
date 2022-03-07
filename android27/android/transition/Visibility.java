package android.transition;

import android.content.*;
import android.util.*;
import android.animation.*;
import android.view.*;

public abstract class Visibility extends Transition
{
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    
    public Visibility() {
        throw new RuntimeException("Stub!");
    }
    
    public Visibility(final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getTransitionProperties() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void captureStartValues(final TransitionValues transitionValues) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void captureEndValues(final TransitionValues transitionValues) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVisible(final TransitionValues values) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, final TransitionValues startValues, final TransitionValues endValues) {
        throw new RuntimeException("Stub!");
    }
    
    public Animator onAppear(final ViewGroup sceneRoot, final TransitionValues startValues, final int startVisibility, final TransitionValues endValues, final int endVisibility) {
        throw new RuntimeException("Stub!");
    }
    
    public Animator onAppear(final ViewGroup sceneRoot, final View view, final TransitionValues startValues, final TransitionValues endValues) {
        throw new RuntimeException("Stub!");
    }
    
    public Animator onDisappear(final ViewGroup sceneRoot, final TransitionValues startValues, final int startVisibility, final TransitionValues endValues, final int endVisibility) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isTransitionRequired(final TransitionValues startValues, final TransitionValues newValues) {
        throw new RuntimeException("Stub!");
    }
    
    public Animator onDisappear(final ViewGroup sceneRoot, final View view, final TransitionValues startValues, final TransitionValues endValues) {
        throw new RuntimeException("Stub!");
    }
}
