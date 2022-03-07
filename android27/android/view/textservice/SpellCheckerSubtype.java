package android.view.textservice;

import android.content.*;
import android.content.pm.*;
import android.os.*;

public final class SpellCheckerSubtype implements Parcelable
{
    public static final Creator<SpellCheckerSubtype> CREATOR;
    
    public SpellCheckerSubtype(final int nameId, final String locale, final String extraValue) {
        throw new RuntimeException("Stub!");
    }
    
    public int getNameResId() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLanguageTag() {
        throw new RuntimeException("Stub!");
    }
    
    public String getExtraValue() {
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
    
    public CharSequence getDisplayName(final Context context, final String packageName, final ApplicationInfo appInfo) {
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
}
