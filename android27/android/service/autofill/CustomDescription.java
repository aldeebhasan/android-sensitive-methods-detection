package android.service.autofill;

import android.os.*;
import android.widget.*;

public final class CustomDescription implements Parcelable
{
    public static final Creator<CustomDescription> CREATOR;
    
    CustomDescription() {
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
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class Builder
    {
        public Builder(final RemoteViews parentPresentation) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addChild(final int id, final Transformation transformation) {
            throw new RuntimeException("Stub!");
        }
        
        public CustomDescription build() {
            throw new RuntimeException("Stub!");
        }
    }
}
