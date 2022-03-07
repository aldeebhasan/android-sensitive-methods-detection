package android.view.inputmethod;

import android.os.*;

public class ExtractedTextRequest implements Parcelable
{
    public static final Creator<ExtractedTextRequest> CREATOR;
    public int flags;
    public int hintMaxChars;
    public int hintMaxLines;
    public int token;
    
    public ExtractedTextRequest() {
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
