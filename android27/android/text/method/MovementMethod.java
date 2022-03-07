package android.text.method;

import android.widget.*;
import android.text.*;
import android.view.*;

public interface MovementMethod
{
    void initialize(final TextView p0, final Spannable p1);
    
    boolean onKeyDown(final TextView p0, final Spannable p1, final int p2, final KeyEvent p3);
    
    boolean onKeyUp(final TextView p0, final Spannable p1, final int p2, final KeyEvent p3);
    
    boolean onKeyOther(final TextView p0, final Spannable p1, final KeyEvent p2);
    
    void onTakeFocus(final TextView p0, final Spannable p1, final int p2);
    
    boolean onTrackballEvent(final TextView p0, final Spannable p1, final MotionEvent p2);
    
    boolean onTouchEvent(final TextView p0, final Spannable p1, final MotionEvent p2);
    
    boolean onGenericMotionEvent(final TextView p0, final Spannable p1, final MotionEvent p2);
    
    boolean canSelectArbitrarily();
}
