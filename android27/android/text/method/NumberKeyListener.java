package android.text.method;

import android.view.*;
import android.text.*;

public abstract class NumberKeyListener extends BaseKeyListener implements InputFilter
{
    public NumberKeyListener() {
        throw new RuntimeException("Stub!");
    }
    
    protected abstract char[] getAcceptedChars();
    
    protected int lookup(final KeyEvent event, final Spannable content) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dstart, final int dend) {
        throw new RuntimeException("Stub!");
    }
    
    protected static boolean ok(final char[] accept, final char c) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final View view, final Editable content, final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
}
