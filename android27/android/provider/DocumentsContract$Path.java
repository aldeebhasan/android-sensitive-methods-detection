package android.provider;

import java.util.*;
import android.os.*;

public static final class Path implements Parcelable
{
    public static final Creator<Path> CREATOR;
    
    public Path(final String rootId, final List<String> path) {
        throw new RuntimeException("Stub!");
    }
    
    public String getRootId() {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getPath() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
