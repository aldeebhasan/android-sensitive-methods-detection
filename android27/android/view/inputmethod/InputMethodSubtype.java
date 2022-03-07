package android.view.inputmethod;

import android.content.*;
import android.content.pm.*;
import android.os.*;

public final class InputMethodSubtype implements Parcelable
{
    public static final Creator<InputMethodSubtype> CREATOR;
    
    public InputMethodSubtype(final int nameId, final int iconId, final String locale, final String mode, final String extraValue, final boolean isAuxiliary, final boolean overridesImplicitlyEnabledSubtype) {
        throw new RuntimeException("Stub!");
    }
    
    public InputMethodSubtype(final int nameId, final int iconId, final String locale, final String mode, final String extraValue, final boolean isAuxiliary, final boolean overridesImplicitlyEnabledSubtype, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public int getNameResId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIconResId() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLanguageTag() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMode() {
        throw new RuntimeException("Stub!");
    }
    
    public String getExtraValue() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAuxiliary() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean overridesImplicitlyEnabledSubtype() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAsciiCapable() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDisplayName(final Context context, final String packageName, final ApplicationInfo appInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean containsExtraValueKey(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public String getExtraValueOf(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class InputMethodSubtypeBuilder
    {
        public InputMethodSubtypeBuilder() {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setIsAuxiliary(final boolean isAuxiliary) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setOverridesImplicitlyEnabledSubtype(final boolean overridesImplicitlyEnabledSubtype) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setIsAsciiCapable(final boolean isAsciiCapable) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setSubtypeIconResId(final int subtypeIconResId) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setSubtypeNameResId(final int subtypeNameResId) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setSubtypeId(final int subtypeId) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setSubtypeLocale(final String subtypeLocale) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setLanguageTag(final String languageTag) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setSubtypeMode(final String subtypeMode) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtypeBuilder setSubtypeExtraValue(final String subtypeExtraValue) {
            throw new RuntimeException("Stub!");
        }
        
        public InputMethodSubtype build() {
            throw new RuntimeException("Stub!");
        }
    }
}
