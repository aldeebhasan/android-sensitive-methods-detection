package android.view.inputmethod;

import android.view.*;
import android.os.*;

public interface InputConnection
{
    public static final int CURSOR_UPDATE_IMMEDIATE = 1;
    public static final int CURSOR_UPDATE_MONITOR = 2;
    public static final int GET_EXTRACTED_TEXT_MONITOR = 1;
    public static final int GET_TEXT_WITH_STYLES = 1;
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
    
    CharSequence getTextBeforeCursor(final int p0, final int p1);
    
    CharSequence getTextAfterCursor(final int p0, final int p1);
    
    CharSequence getSelectedText(final int p0);
    
    int getCursorCapsMode(final int p0);
    
    ExtractedText getExtractedText(final ExtractedTextRequest p0, final int p1);
    
    boolean deleteSurroundingText(final int p0, final int p1);
    
    boolean deleteSurroundingTextInCodePoints(final int p0, final int p1);
    
    boolean setComposingText(final CharSequence p0, final int p1);
    
    boolean setComposingRegion(final int p0, final int p1);
    
    boolean finishComposingText();
    
    boolean commitText(final CharSequence p0, final int p1);
    
    boolean commitCompletion(final CompletionInfo p0);
    
    boolean commitCorrection(final CorrectionInfo p0);
    
    boolean setSelection(final int p0, final int p1);
    
    boolean performEditorAction(final int p0);
    
    boolean performContextMenuAction(final int p0);
    
    boolean beginBatchEdit();
    
    boolean endBatchEdit();
    
    boolean sendKeyEvent(final KeyEvent p0);
    
    boolean clearMetaKeyStates(final int p0);
    
    boolean reportFullscreenMode(final boolean p0);
    
    boolean performPrivateCommand(final String p0, final Bundle p1);
    
    boolean requestCursorUpdates(final int p0);
    
    Handler getHandler();
    
    void closeConnection();
    
    boolean commitContent(final InputContentInfo p0, final int p1, final Bundle p2);
}
