package android.inputmethodservice;

public interface OnKeyboardActionListener
{
    void onPress(final int p0);
    
    void onRelease(final int p0);
    
    void onKey(final int p0, final int[] p1);
    
    void onText(final CharSequence p0);
    
    void swipeLeft();
    
    void swipeRight();
    
    void swipeDown();
    
    void swipeUp();
}
