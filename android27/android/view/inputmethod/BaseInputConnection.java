package android.view.inputmethod;

import android.text.*;
import android.os.*;
import android.view.*;

public class BaseInputConnection implements InputConnection
{
    public BaseInputConnection(final View targetView, final boolean fullEditor) {
        throw new RuntimeException("Stub!");
    }
    
    public static final void removeComposingSpans(final Spannable text) {
        throw new RuntimeException("Stub!");
    }
    
    public static void setComposingSpans(final Spannable text) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getComposingSpanStart(final Spannable text) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getComposingSpanEnd(final Spannable text) {
        throw new RuntimeException("Stub!");
    }
    
    public Editable getEditable() {
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
    public void closeConnection() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean clearMetaKeyStates(final int states) {
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
    public boolean commitText(final CharSequence text, final int newCursorPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteSurroundingText(final int beforeLength, final int afterLength) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteSurroundingTextInCodePoints(final int beforeLength, final int afterLength) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean finishComposingText() {
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
    public CharSequence getTextBeforeCursor(final int length, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getSelectedText(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getTextAfterCursor(final int length, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performEditorAction(final int actionCode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performContextMenuAction(final int id) {
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
    public boolean setComposingText(final CharSequence text, final int newCursorPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setComposingRegion(final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean setSelection(final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean sendKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean reportFullscreenMode(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean commitContent(final InputContentInfo inputContentInfo, final int flags, final Bundle opts) {
        throw new RuntimeException("Stub!");
    }
}
