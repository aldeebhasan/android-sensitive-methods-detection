package android.preference;

import android.view.*;
import android.os.*;

public static class BaseSavedState extends AbsSavedState
{
    public static final Parcelable.Creator<BaseSavedState> CREATOR;
    
    public BaseSavedState(final Parcel source) {
        super(null, null);
        throw new RuntimeException("Stub!");
    }
    
    public BaseSavedState(final Parcelable superState) {
        super(null, null);
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
