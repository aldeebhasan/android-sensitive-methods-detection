package android.text.style;

import android.content.*;
import java.util.*;
import android.os.*;
import android.text.*;

public class SuggestionSpan extends CharacterStyle implements ParcelableSpan
{
    public static final String ACTION_SUGGESTION_PICKED = "android.text.style.SUGGESTION_PICKED";
    public static final Parcelable.Creator<SuggestionSpan> CREATOR;
    public static final int FLAG_AUTO_CORRECTION = 4;
    public static final int FLAG_EASY_CORRECT = 1;
    public static final int FLAG_MISSPELLED = 2;
    public static final int SUGGESTIONS_MAX_SIZE = 5;
    public static final String SUGGESTION_SPAN_PICKED_AFTER = "after";
    public static final String SUGGESTION_SPAN_PICKED_BEFORE = "before";
    public static final String SUGGESTION_SPAN_PICKED_HASHCODE = "hashcode";
    
    public SuggestionSpan(final Context context, final String[] suggestions, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public SuggestionSpan(final Locale locale, final String[] suggestions, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public SuggestionSpan(final Context context, final Locale locale, final String[] suggestions, final int flags, final Class<?> notificationTargetClass) {
        throw new RuntimeException("Stub!");
    }
    
    public SuggestionSpan(final Parcel src) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getSuggestions() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public Locale getLocaleObject() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFlags(final int flags) {
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
    
    @Override
    public int getSpanTypeId() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateDrawState(final TextPaint tp) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
