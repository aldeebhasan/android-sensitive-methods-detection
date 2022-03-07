package android.content;

import android.os.*;

public class ClipDescription implements Parcelable
{
    public static final Creator<ClipDescription> CREATOR;
    public static final String MIMETYPE_TEXT_HTML = "text/html";
    public static final String MIMETYPE_TEXT_INTENT = "text/vnd.android.intent";
    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static final String MIMETYPE_TEXT_URILIST = "text/uri-list";
    
    public ClipDescription(final CharSequence label, final String[] mimeTypes) {
        throw new RuntimeException("Stub!");
    }
    
    public ClipDescription(final ClipDescription o) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean compareMimeTypes(final String concreteType, final String desiredType) {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimestamp() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasMimeType(final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] filterMimeTypes(final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMimeTypeCount() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMimeType(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public void setExtras(final PersistableBundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
