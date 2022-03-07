package android.app.assist;

import android.content.*;
import android.net.*;
import android.os.*;

public class AssistContent implements Parcelable
{
    public static final Creator<AssistContent> CREATOR;
    
    public AssistContent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAppProvidedIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setClipData(final ClipData clip) {
        throw new RuntimeException("Stub!");
    }
    
    public ClipData getClipData() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStructuredData(final String structuredData) {
        throw new RuntimeException("Stub!");
    }
    
    public String getStructuredData() {
        throw new RuntimeException("Stub!");
    }
    
    public void setWebUri(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getWebUri() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAppProvidedWebUri() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
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
