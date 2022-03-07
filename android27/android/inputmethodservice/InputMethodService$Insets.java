package android.inputmethodservice;

import android.graphics.*;

public static final class Insets
{
    public static final int TOUCHABLE_INSETS_CONTENT = 1;
    public static final int TOUCHABLE_INSETS_FRAME = 0;
    public static final int TOUCHABLE_INSETS_REGION = 3;
    public static final int TOUCHABLE_INSETS_VISIBLE = 2;
    public int contentTopInsets;
    public int touchableInsets;
    public final Region touchableRegion;
    public int visibleTopInsets;
    
    public Insets() {
        throw new RuntimeException("Stub!");
    }
}
