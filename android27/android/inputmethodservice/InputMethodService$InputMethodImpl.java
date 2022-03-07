package android.inputmethodservice;

import android.os.*;
import android.view.inputmethod.*;

public class InputMethodImpl extends AbstractInputMethodImpl
{
    public InputMethodImpl() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void attachToken(final IBinder token) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void bindInput(final InputBinding binding) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void unbindInput() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startInput(final InputConnection ic, final EditorInfo attribute) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void restartInput(final InputConnection ic, final EditorInfo attribute) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void hideSoftInput(final int flags, final ResultReceiver resultReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void showSoftInput(final int flags, final ResultReceiver resultReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void changeInputMethodSubtype(final InputMethodSubtype subtype) {
        throw new RuntimeException("Stub!");
    }
}
