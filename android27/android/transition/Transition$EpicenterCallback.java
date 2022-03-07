package android.transition;

import android.graphics.*;

public abstract static class EpicenterCallback
{
    public EpicenterCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Rect onGetEpicenter(final Transition p0);
}
