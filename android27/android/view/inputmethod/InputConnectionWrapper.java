package android.view.inputmethod;

import android.view.*;
import android.os.*;

public class InputConnectionWrapper implements InputConnection
{
    public InputConnectionWrapper(final InputConnection target, final boolean mutable) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTarget(final InputConnection target) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getTextBeforeCursor(final int n, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getTextAfterCursor(final int n, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getSelectedText(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getCursorCapsMode(final int reqModes) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ExtractedText getExtractedText(final ExtractedTextRequest request, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteSurroundingTextInCodePoints(final int beforeLength, final int afterLength) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteSurroundingText(final int beforeLength, final int afterLength) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setComposingText(final CharSequence text, final int newCursorPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setComposingRegion(final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean finishComposingText() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean commitText(final CharSequence text, final int newCursorPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean commitCompletion(final CompletionInfo text) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean commitCorrection(final CorrectionInfo correctionInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setSelection(final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performEditorAction(final int editorAction) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performContextMenuAction(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean beginBatchEdit() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean endBatchEdit() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean sendKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean clearMetaKeyStates(final int states) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean reportFullscreenMode(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performPrivateCommand(final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean requestCursorUpdates(final int cursorUpdateMode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Handler getHandler() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void closeConnection() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean commitContent(final InputContentInfo inputContentInfo, final int flags, final Bundle opts) {
        throw new RuntimeException("Stub!");
    }
}
