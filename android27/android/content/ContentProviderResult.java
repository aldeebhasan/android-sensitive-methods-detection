package android.content;

import android.net.*;
import android.os.*;

public class ContentProviderResult implements Parcelable
{
    public static final Creator<ContentProviderResult> CREATOR;
    public final Integer count;
    public final Uri uri;
    
    public ContentProviderResult(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProviderResult(final int count) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProviderResult(final Parcel source) {
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
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
