package android.service.autofill;

import java.util.*;
import android.os.*;

public final class SaveRequest implements Parcelable
{
    public static final Creator<SaveRequest> CREATOR;
    
    SaveRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public List<FillContext> getFillContexts() {
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
