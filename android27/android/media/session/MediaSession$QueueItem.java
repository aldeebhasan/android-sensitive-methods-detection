package android.media.session;

import android.media.*;
import android.os.*;

public static final class QueueItem implements Parcelable
{
    public static final Creator<QueueItem> CREATOR;
    public static final int UNKNOWN_ID = -1;
    
    public QueueItem(final MediaDescription description, final long id) {
        throw new RuntimeException("Stub!");
    }
    
    public MediaDescription getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public long getQueueId() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
