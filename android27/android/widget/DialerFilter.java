package android.widget;

import android.content.*;
import android.util.*;
import android.graphics.*;
import android.view.*;
import android.text.*;

@Deprecated
public class DialerFilter extends RelativeLayout
{
    public static final int DIGITS_AND_LETTERS = 1;
    public static final int DIGITS_AND_LETTERS_NO_DIGITS = 2;
    public static final int DIGITS_AND_LETTERS_NO_LETTERS = 3;
    public static final int DIGITS_ONLY = 4;
    public static final int LETTERS_ONLY = 5;
    
    public DialerFilter(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public DialerFilter(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onFinishInflate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onFocusChanged(final boolean focused, final int direction, final Rect previouslyFocusedRect) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isQwertyKeyboard() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMode(final int newMode) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLetters() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDigits() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getFilterText() {
        throw new RuntimeException("Stub!");
    }
    
    public void append(final String text) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLettersWatcher(final TextWatcher watcher) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDigitsWatcher(final TextWatcher watcher) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFilterWatcher(final TextWatcher watcher) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeFilterWatcher(final TextWatcher watcher) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onModeChange(final int oldMode, final int newMode) {
        throw new RuntimeException("Stub!");
    }
}
