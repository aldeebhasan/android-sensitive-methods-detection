package android.view;

import android.view.accessibility.*;
import java.util.*;

public interface Callback
{
    boolean dispatchKeyEvent(final KeyEvent p0);
    
    boolean dispatchKeyShortcutEvent(final KeyEvent p0);
    
    boolean dispatchTouchEvent(final MotionEvent p0);
    
    boolean dispatchTrackballEvent(final MotionEvent p0);
    
    boolean dispatchGenericMotionEvent(final MotionEvent p0);
    
    boolean dispatchPopulateAccessibilityEvent(final AccessibilityEvent p0);
    
    View onCreatePanelView(final int p0);
    
    boolean onCreatePanelMenu(final int p0, final Menu p1);
    
    boolean onPreparePanel(final int p0, final View p1, final Menu p2);
    
    boolean onMenuOpened(final int p0, final Menu p1);
    
    boolean onMenuItemSelected(final int p0, final MenuItem p1);
    
    void onWindowAttributesChanged(final WindowManager.LayoutParams p0);
    
    void onContentChanged();
    
    void onWindowFocusChanged(final boolean p0);
    
    void onAttachedToWindow();
    
    void onDetachedFromWindow();
    
    void onPanelClosed(final int p0, final Menu p1);
    
    boolean onSearchRequested();
    
    boolean onSearchRequested(final SearchEvent p0);
    
    ActionMode onWindowStartingActionMode(final ActionMode.Callback p0);
    
    ActionMode onWindowStartingActionMode(final ActionMode.Callback p0, final int p1);
    
    void onActionModeStarted(final ActionMode p0);
    
    void onActionModeFinished(final ActionMode p0);
    
    default void onProvideKeyboardShortcuts(final List<KeyboardShortcutGroup> data, final Menu menu, final int deviceId) {
        throw new RuntimeException("Stub!");
    }
    
    default void onPointerCaptureChanged(final boolean hasCapture) {
        throw new RuntimeException("Stub!");
    }
}
