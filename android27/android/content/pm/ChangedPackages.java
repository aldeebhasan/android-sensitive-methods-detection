package android.content.pm;

import java.util.*;
import android.os.*;

public final class ChangedPackages implements Parcelable
{
    public static final Creator<ChangedPackages> CREATOR;
    
    public ChangedPackages(final int sequenceNumber, final List<String> packageNames) {
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
    
    public int getSequenceNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getPackageNames() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
