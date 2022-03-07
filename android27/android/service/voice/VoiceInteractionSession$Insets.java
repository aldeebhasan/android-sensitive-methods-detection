package android.service.voice;

import android.graphics.*;

public static final class Insets
{
    public static final int TOUCHABLE_INSETS_CONTENT = 1;
    public static final int TOUCHABLE_INSETS_FRAME = 0;
    public static final int TOUCHABLE_INSETS_REGION = 3;
    public final Rect contentInsets;
    public int touchableInsets;
    public final Region touchableRegion;
    
    public Insets() {
        throw new RuntimeException("Stub!");
    }
}
