package android.os;

import java.util.*;

public final class LocaleList implements Parcelable
{
    public static final Creator<LocaleList> CREATOR;
    
    public LocaleList(final Locale... list) {
        throw new RuntimeException("Stub!");
    }
    
    public Locale get(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }
    
    public int size() {
        throw new RuntimeException("Stub!");
    }
    
    public int indexOf(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object other) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public String toLanguageTags() {
        throw new RuntimeException("Stub!");
    }
    
    public static LocaleList getEmptyLocaleList() {
        throw new RuntimeException("Stub!");
    }
    
    public static LocaleList forLanguageTags(final String list) {
        throw new RuntimeException("Stub!");
    }
    
    public Locale getFirstMatch(final String[] supportedLocales) {
        throw new RuntimeException("Stub!");
    }
    
    public static LocaleList getDefault() {
        throw new RuntimeException("Stub!");
    }
    
    public static LocaleList getAdjustedDefault() {
        throw new RuntimeException("Stub!");
    }
    
    public static void setDefault(final LocaleList locales) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
