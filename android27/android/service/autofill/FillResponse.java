package android.service.autofill;

import android.view.autofill.*;
import android.content.*;
import android.widget.*;
import android.os.*;

public final class FillResponse implements Parcelable
{
    public static final Creator<FillResponse> CREATOR;
    
    FillResponse() {
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
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAuthentication(final AutofillId[] ids, final IntentSender authentication, final RemoteViews presentation) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIgnoredIds(final AutofillId... ids) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addDataset(final Dataset dataset) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSaveInfo(final SaveInfo saveInfo) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setClientState(final Bundle clientState) {
            throw new RuntimeException("Stub!");
        }
        
        public FillResponse build() {
            throw new RuntimeException("Stub!");
        }
    }
}
