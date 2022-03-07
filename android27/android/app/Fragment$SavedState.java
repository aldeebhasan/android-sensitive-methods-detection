package android.app;

import android.os.*;

public static class SavedState implements Parcelable
{
    public static final ClassLoaderCreator<SavedState> CREATOR;
    
    SavedState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
