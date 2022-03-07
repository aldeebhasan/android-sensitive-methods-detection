package android.content;

import android.os.*;

public static class ShortcutIconResource implements Parcelable
{
    public static final Creator<ShortcutIconResource> CREATOR;
    public String packageName;
    public String resourceName;
    
    public ShortcutIconResource() {
        throw new RuntimeException("Stub!");
    }
    
    public static ShortcutIconResource fromContext(final Context context, final int resourceId) {
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
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
