package android.service.autofill;

import android.app.assist.*;
import android.os.*;

public final class FillContext implements Parcelable
{
    public static final Creator<FillContext> CREATOR;
    
    FillContext() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRequestId() {
        throw new RuntimeException("Stub!");
    }
    
    public AssistStructure getStructure() {
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
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
