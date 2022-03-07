package android.service.autofill;

import android.view.autofill.*;
import android.os.*;

public final class LuhnChecksumValidator implements Validator, Parcelable
{
    public static final Creator<LuhnChecksumValidator> CREATOR;
    
    public LuhnChecksumValidator(final AutofillId... ids) {
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
