package android.text.style;

import android.os.*;
import android.text.*;

public class RelativeSizeSpan extends MetricAffectingSpan implements ParcelableSpan
{
    public RelativeSizeSpan(final float proportion) {
        throw new RuntimeException("Stub!");
    }
    
    public RelativeSizeSpan(final Parcel src) {
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
    
    public float getSizeChange() {
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
