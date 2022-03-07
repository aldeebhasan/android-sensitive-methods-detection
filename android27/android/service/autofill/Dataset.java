package android.service.autofill;

import android.os.*;
import android.widget.*;
import android.content.*;
import android.view.autofill.*;

public final class Dataset implements Parcelable
{
    public static final Creator<Dataset> CREATOR;
    
    Dataset() {
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
    
    public static final class Builder
    {
        public Builder(final RemoteViews presentation) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAuthentication(final IntentSender authentication) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setId(final String id) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setValue(final AutofillId id, final AutofillValue value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setValue(final AutofillId id, final AutofillValue value, final RemoteViews presentation) {
            throw new RuntimeException("Stub!");
        }
        
        public Dataset build() {
            throw new RuntimeException("Stub!");
        }
    }
}
