package android.text.style;

import android.text.*;
import android.os.*;

public static class Standard implements AlignmentSpan, ParcelableSpan
{
    public Standard(final Layout.Alignment align) {
        throw new RuntimeException("Stub!");
    }
    
    public Standard(final Parcel src) {
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
    
    @Override
    public Layout.Alignment getAlignment() {
        throw new RuntimeException("Stub!");
    }
}
