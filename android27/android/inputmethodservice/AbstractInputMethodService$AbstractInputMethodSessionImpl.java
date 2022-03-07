package android.inputmethodservice;

import android.view.inputmethod.*;
import android.view.*;

public abstract class AbstractInputMethodSessionImpl implements InputMethodSession
{
    public AbstractInputMethodSessionImpl() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRevoked() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void revokeSelf() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchKeyEvent(final int seq, final KeyEvent event, final EventCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchTrackballEvent(final int seq, final MotionEvent event, final EventCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchGenericMotionEvent(final int seq, final MotionEvent event, final EventCallback callback) {
        throw new RuntimeException("Stub!");
    }
}
