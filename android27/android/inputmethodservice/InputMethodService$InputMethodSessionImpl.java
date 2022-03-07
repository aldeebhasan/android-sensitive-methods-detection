package android.inputmethodservice;

import android.graphics.*;
import android.os.*;
import android.view.inputmethod.*;

public class InputMethodSessionImpl extends AbstractInputMethodSessionImpl
{
    public InputMethodSessionImpl() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void finishInput() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void displayCompletions(final CompletionInfo[] completions) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateExtractedText(final int token, final ExtractedText text) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateSelection(final int oldSelStart, final int oldSelEnd, final int newSelStart, final int newSelEnd, final int candidatesStart, final int candidatesEnd) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void viewClicked(final boolean focusChanged) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateCursor(final Rect newCursor) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void appPrivateCommand(final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void toggleSoftInput(final int showFlags, final int hideFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateCursorAnchorInfo(final CursorAnchorInfo info) {
        throw new RuntimeException("Stub!");
    }
}
