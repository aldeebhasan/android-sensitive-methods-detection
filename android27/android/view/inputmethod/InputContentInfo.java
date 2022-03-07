package android.view.inputmethod;

import android.net.*;
import android.content.*;
import android.os.*;

public final class InputContentInfo implements Parcelable
{
    public static final Creator<InputContentInfo> CREATOR;
    
    public InputContentInfo(final Uri contentUri, final ClipDescription description) {
        throw new RuntimeException("Stub!");
    }
    
    public InputContentInfo(final Uri contentUri, final ClipDescription description, final Uri linkUri) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getContentUri() {
        throw new RuntimeException("Stub!");
    }
    
    public ClipDescription getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getLinkUri() {
        throw new RuntimeException("Stub!");
    }
    
    public void requestPermission() {
        throw new RuntimeException("Stub!");
    }
    
    public void releasePermission() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
