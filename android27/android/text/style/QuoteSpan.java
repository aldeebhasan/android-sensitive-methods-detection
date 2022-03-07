package android.text.style;

import android.os.*;
import android.graphics.*;
import android.text.*;

public class QuoteSpan implements LeadingMarginSpan, ParcelableSpan
{
    public QuoteSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public QuoteSpan(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public QuoteSpan(final Parcel src) {
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
    
    public int getColor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLeadingMargin(final boolean first) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void drawLeadingMargin(final Canvas c, final Paint p, final int x, final int dir, final int top, final int baseline, final int bottom, final CharSequence text, final int start, final int end, final boolean first, final Layout layout) {
        throw new RuntimeException("Stub!");
    }
}
