package android.text.style;

import android.os.*;
import android.graphics.*;
import android.text.*;

public class BulletSpan implements LeadingMarginSpan, ParcelableSpan
{
    public static final int STANDARD_GAP_WIDTH = 2;
    
    public BulletSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public BulletSpan(final int gapWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public BulletSpan(final int gapWidth, final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public BulletSpan(final Parcel src) {
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
    public int getLeadingMargin(final boolean first) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void drawLeadingMargin(final Canvas c, final Paint p, final int x, final int dir, final int top, final int baseline, final int bottom, final CharSequence text, final int start, final int end, final boolean first, final Layout l) {
        throw new RuntimeException("Stub!");
    }
}
