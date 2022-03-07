package android.service.autofill;

import android.view.autofill.*;
import java.util.regex.*;
import android.os.*;

public final class RegexValidator implements Validator, Parcelable
{
    public static final Creator<RegexValidator> CREATOR;
    
    public RegexValidator(final AutofillId id, final Pattern regex) {
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
