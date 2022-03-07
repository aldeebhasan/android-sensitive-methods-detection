package android.animation;

public interface AnimatorListener
{
    default void onAnimationStart(final Animator animation, final boolean isReverse) {
        throw new RuntimeException("Stub!");
    }
    
    default void onAnimationEnd(final Animator animation, final boolean isReverse) {
        throw new RuntimeException("Stub!");
    }
    
    void onAnimationStart(final Animator p0);
    
    void onAnimationEnd(final Animator p0);
    
    void onAnimationCancel(final Animator p0);
    
    void onAnimationRepeat(final Animator p0);
}
