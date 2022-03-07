package android.text.style;

import android.os.*;
import android.text.*;

public class AbsoluteSizeSpan extends MetricAffectingSpan implements ParcelableSpan
{
    public AbsoluteSizeSpan(final int size) {
        throw new RuntimeException("Stub!");
    }
    
    public AbsoluteSizeSpan(final int size, final boolean dip) {
        throw new RuntimeException("Stub!");
    }
    
    public AbsoluteSizeSpan(final Parcel src) {
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
    
    public int getSize() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getDip() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateDrawState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateMeasureState(final TextPaint ds) {
        throw new RuntimeException("Stub!");
    }
}
