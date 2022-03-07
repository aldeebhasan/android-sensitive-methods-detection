package android.view;

import android.os.*;

public final class WindowContentFrameStats extends FrameStats implements Parcelable
{
    public static final Creator<WindowContentFrameStats> CREATOR;
    
    WindowContentFrameStats() {
        throw new RuntimeException("Stub!");
    }
    
    public long getFramePostedTimeNano(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public long getFrameReadyTimeNano(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
