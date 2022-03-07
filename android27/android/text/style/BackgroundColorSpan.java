package android.text.style;

import android.os.*;
import android.text.*;

public class BackgroundColorSpan extends CharacterStyle implements UpdateAppearance, ParcelableSpan
{
    public BackgroundColorSpan(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public BackgroundColorSpan(final Parcel src) {
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
    
    public int getBackgroundColor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateDrawState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
}
