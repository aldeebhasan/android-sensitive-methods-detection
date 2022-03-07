package android.view.inputmethod;

import android.os.*;
import android.view.*;
import java.util.*;

public final class InputMethodManager
{
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    public static final int RESULT_HIDDEN = 3;
    public static final int RESULT_SHOWN = 2;
    public static final int RESULT_UNCHANGED_HIDDEN = 1;
    public static final int RESULT_UNCHANGED_SHOWN = 0;
    public static final int SHOW_FORCED = 2;
    public static final int SHOW_IMPLICIT = 1;
    
    InputMethodManager() {
        throw new RuntimeException("Stub!");
    }
    
    public List<InputMethodInfo> getInputMethodList() {
        throw new RuntimeException("Stub!");
    }
    
    public List<InputMethodInfo> getEnabledInputMethodList() {
        throw new RuntimeException("Stub!");
    }
    
    public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(final InputMethodInfo imi, final boolean allowsImplicitlySelectedSubtypes) {
        throw new RuntimeException("Stub!");
    }
    
    public void showStatusIcon(final IBinder imeToken, final String packageName, final int iconId) {
        throw new RuntimeException("Stub!");
    }
    
    public void hideStatusIcon(final IBinder imeToken) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFullscreenMode() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActive(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActive() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAcceptingText() {
        throw new RuntimeException("Stub!");
    }
    
    public void displayCompletions(final View view, final CompletionInfo[] completions) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateExtractedText(final View view, final int token, final ExtractedText text) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean showSoftInput(final View view, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean showSoftInput(final View view, final int flags, final ResultReceiver resultReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hideSoftInputFromWindow(final IBinder windowToken, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hideSoftInputFromWindow(final IBinder windowToken, final int flags, final ResultReceiver resultReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    public void toggleSoftInputFromWindow(final IBinder windowToken, final int showFlags, final int hideFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public void toggleSoftInput(final int showFlags, final int hideFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public void restartInput(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateSelection(final View view, final int selStart, final int selEnd, final int candidatesStart, final int candidatesEnd) {
        throw new RuntimeException("Stub!");
    }
    
    public void viewClicked(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean isWatchingCursor(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void updateCursor(final View view, final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateCursorAnchorInfo(final View view, final CursorAnchorInfo cursorAnchorInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAppPrivateCommand(final View view, final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInputMethod(final IBinder token, final String id) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInputMethodAndSubtype(final IBinder token, final String id, final InputMethodSubtype subtype) {
        throw new RuntimeException("Stub!");
    }
    
    public void hideSoftInputFromInputMethod(final IBinder token, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void showSoftInputFromInputMethod(final IBinder token, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchKeyEventFromInputMethod(final View targetView, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void showInputMethodPicker() {
        throw new RuntimeException("Stub!");
    }
    
    public void showInputMethodAndSubtypeEnabler(final String imiId) {
        throw new RuntimeException("Stub!");
    }
    
    public InputMethodSubtype getCurrentInputMethodSubtype() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setCurrentInputMethodSubtype(final InputMethodSubtype subtype) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<InputMethodInfo, List<InputMethodSubtype>> getShortcutInputMethodsAndSubtypes() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean switchToLastInputMethod(final IBinder imeToken) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean switchToNextInputMethod(final IBinder imeToken, final boolean onlyCurrentIme) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean shouldOfferSwitchingToNextInputMethod(final IBinder imeToken) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAdditionalInputMethodSubtypes(final String imiId, final InputMethodSubtype[] subtypes) {
        throw new RuntimeException("Stub!");
    }
    
    public InputMethodSubtype getLastInputMethodSubtype() {
        throw new RuntimeException("Stub!");
    }
}
