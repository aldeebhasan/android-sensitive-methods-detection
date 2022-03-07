package android.service.autofill;

import android.os.*;
import android.view.autofill.*;
import java.util.regex.*;

public final class CharSequenceTransformation implements Transformation, Parcelable
{
    public static final Creator<CharSequenceTransformation> CREATOR;
    
    CharSequenceTransformation() {
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
    
    public static class Builder
    {
        public Builder(final AutofillId id, final Pattern regex, final String subst) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addField(final AutofillId id, final Pattern regex, final String subst) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequenceTransformation build() {
            throw new RuntimeException("Stub!");
        }
    }
}
