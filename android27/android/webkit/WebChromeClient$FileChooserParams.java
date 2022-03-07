package android.webkit;

import android.content.*;
import android.net.*;

public abstract static class FileChooserParams
{
    public static final int MODE_OPEN = 0;
    public static final int MODE_OPEN_MULTIPLE = 1;
    public static final int MODE_SAVE = 3;
    
    public FileChooserParams() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri[] parseResult(final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getMode();
    
    public abstract String[] getAcceptTypes();
    
    public abstract boolean isCaptureEnabled();
    
    public abstract CharSequence getTitle();
    
    public abstract String getFilenameHint();
    
    public abstract Intent createIntent();
}
