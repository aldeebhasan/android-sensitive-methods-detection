package android.content.res;

import android.os.*;

public class ObbInfo implements Parcelable
{
    public static final Creator<ObbInfo> CREATOR;
    public static final int OBB_OVERLAY = 1;
    public String filename;
    public int flags;
    public String packageName;
    public int version;
    
    ObbInfo() {
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
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
