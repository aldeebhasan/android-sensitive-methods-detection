package android.text.method;

import android.text.*;
import android.view.*;

public interface KeyListener
{
    int getInputType();
    
    boolean onKeyDown(final View p0, final Editable p1, final int p2, final KeyEvent p3);
    
    boolean onKeyUp(final View p0, final Editable p1, final int p2, final KeyEvent p3);
    
    boolean onKeyOther(final View p0, final Editable p1, final KeyEvent p2);
    
    void clearMetaKeyState(final View p0, final Editable p1, final int p2);
}
