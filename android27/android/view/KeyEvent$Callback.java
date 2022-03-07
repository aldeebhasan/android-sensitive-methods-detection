package android.view;

public interface Callback
{
    boolean onKeyDown(final int p0, final KeyEvent p1);
    
    boolean onKeyLongPress(final int p0, final KeyEvent p1);
    
    boolean onKeyUp(final int p0, final KeyEvent p1);
    
    boolean onKeyMultiple(final int p0, final int p1, final KeyEvent p2);
}
