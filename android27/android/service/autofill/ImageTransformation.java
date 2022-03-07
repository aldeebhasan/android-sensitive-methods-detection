package android.service.autofill;

import android.os.*;
import android.view.autofill.*;
import java.util.regex.*;

public final class ImageTransformation implements Transformation, Parcelable
{
    public static final Creator<ImageTransformation> CREATOR;
    
    ImageTransformation() {
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
        public Builder(final AutofillId id, final Pattern regex, final int resId) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addOption(final Pattern regex, final int resId) {
            throw new RuntimeException("Stub!");
        }
        
        public ImageTransformation build() {
            throw new RuntimeException("Stub!");
        }
    }
}
