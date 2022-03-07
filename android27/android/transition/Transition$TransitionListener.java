package android.transition;

public interface TransitionListener
{
    void onTransitionStart(final Transition p0);
    
    void onTransitionEnd(final Transition p0);
    
    void onTransitionCancel(final Transition p0);
    
    void onTransitionPause(final Transition p0);
    
    void onTransitionResume(final Transition p0);
}
