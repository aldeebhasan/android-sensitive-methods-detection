package android.service.autofill;

import java.util.*;
import android.os.*;

public final class FillRequest implements Parcelable
{
    public static final Creator<FillRequest> CREATOR;
    public static final int FLAG_MANUAL_REQUEST = 1;
    
    FillRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public List<FillContext> getFillContexts() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getClientState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
