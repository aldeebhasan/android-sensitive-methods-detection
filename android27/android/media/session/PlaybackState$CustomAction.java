package android.media.session;

import android.os.*;

public static final class CustomAction implements Parcelable
{
    public static final Creator<CustomAction> CREATOR;
    
    CustomAction() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public String getAction() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Builder
    {
        public Builder(final String action, final CharSequence name, final int icon) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setExtras(final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public CustomAction build() {
            throw new RuntimeException("Stub!");
        }
    }
}
