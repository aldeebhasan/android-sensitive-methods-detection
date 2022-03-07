package android.text.style;

import android.text.*;
import android.os.*;
import android.view.*;

public class URLSpan extends ClickableSpan implements ParcelableSpan
{
    public URLSpan(final String url) {
        throw new RuntimeException("Stub!");
    }
    
    public URLSpan(final Parcel src) {
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
    
    public String getURL() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onClick(final View widget) {
        throw new RuntimeException("Stub!");
    }
}
