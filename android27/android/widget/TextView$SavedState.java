package android.widget;

import android.view.*;
import android.os.*;

public static class SavedState extends BaseSavedState
{
    public static final Parcelable.Creator<SavedState> CREATOR;
    
    SavedState() {
        super((Parcelable)null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
