package android.service.autofill;

import java.util.*;
import android.os.*;

public final class FillEventHistory implements Parcelable
{
    public static final Creator<FillEventHistory> CREATOR;
    
    FillEventHistory() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getClientState() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Event> getEvents() {
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
    
    static {
        CREATOR = null;
    }
    
    public static final class Event
    {
        public static final int TYPE_AUTHENTICATION_SELECTED = 2;
        public static final int TYPE_DATASET_AUTHENTICATION_SELECTED = 1;
        public static final int TYPE_DATASET_SELECTED = 0;
        public static final int TYPE_SAVE_SHOWN = 3;
        
        Event() {
            throw new RuntimeException("Stub!");
        }
        
        public int getType() {
            throw new RuntimeException("Stub!");
        }
        
        public String getDatasetId() {
            throw new RuntimeException("Stub!");
        }
    }
}
