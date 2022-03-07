package android.view.inputmethod;

import android.os.*;

public final class CorrectionInfo implements Parcelable
{
    public static final Creator<CorrectionInfo> CREATOR;
    
    public CorrectionInfo(final int offset, final CharSequence oldText, final CharSequence newText) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOffset() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getOldText() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getNewText() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
