package android.inputmethodservice;

import android.view.inputmethod.*;

public abstract class AbstractInputMethodImpl implements InputMethod
{
    public AbstractInputMethodImpl() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void createSession(final SessionCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setSessionEnabled(final InputMethodSession session, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void revokeSession(final InputMethodSession session) {
        throw new RuntimeException("Stub!");
    }
}
