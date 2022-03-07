package android.text.style;

import android.os.*;
import android.text.*;

public class ForegroundColorSpan extends CharacterStyle implements UpdateAppearance, ParcelableSpan
{
    public ForegroundColorSpan(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public ForegroundColorSpan(final Parcel src) {
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
    
    public int getForegroundColor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateDrawState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
}
