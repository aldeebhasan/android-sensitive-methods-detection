package android.service.autofill;

import android.os.*;
import android.view.autofill.*;
import android.content.*;

public final class SaveInfo implements Parcelable
{
    public static final Creator<SaveInfo> CREATOR;
    public static final int FLAG_SAVE_ON_ALL_VIEWS_INVISIBLE = 1;
    public static final int NEGATIVE_BUTTON_STYLE_CANCEL = 0;
    public static final int NEGATIVE_BUTTON_STYLE_REJECT = 1;
    public static final int SAVE_DATA_TYPE_ADDRESS = 2;
    public static final int SAVE_DATA_TYPE_CREDIT_CARD = 4;
    public static final int SAVE_DATA_TYPE_EMAIL_ADDRESS = 16;
    public static final int SAVE_DATA_TYPE_GENERIC = 0;
    public static final int SAVE_DATA_TYPE_PASSWORD = 1;
    public static final int SAVE_DATA_TYPE_USERNAME = 8;
    
    SaveInfo() {
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
        public Builder(final int type, final AutofillId[] requiredIds) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setFlags(final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOptionalIds(final AutofillId[] ids) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDescription(final CharSequence description) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setCustomDescription(final CustomDescription customDescription) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setNegativeAction(final int style, final IntentSender listener) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setValidator(final Validator validator) {
            throw new RuntimeException("Stub!");
        }
        
        public SaveInfo build() {
            throw new RuntimeException("Stub!");
        }
    }
}
