package android.text.style;

import java.util.*;
import android.os.*;
import android.text.*;

public class LocaleSpan extends MetricAffectingSpan implements ParcelableSpan
{
    public LocaleSpan(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public LocaleSpan(final LocaleList locales) {
        throw new RuntimeException("Stub!");
    }
    
    public LocaleSpan(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getSpanTypeId() {
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
    
    public Locale getLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public LocaleList getLocales() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateDrawState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateMeasureState(final TextPaint paint) {
        throw new RuntimeException("Stub!");
    }
}
