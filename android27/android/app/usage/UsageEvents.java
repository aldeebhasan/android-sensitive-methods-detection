package android.app.usage;

import android.os.*;
import android.content.res.*;

public final class UsageEvents implements Parcelable
{
    public static final Creator<UsageEvents> CREATOR;
    
    UsageEvents() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasNextEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getNextEvent(final Event eventOut) {
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
        public static final int CONFIGURATION_CHANGE = 5;
        public static final int MOVE_TO_BACKGROUND = 2;
        public static final int MOVE_TO_FOREGROUND = 1;
        public static final int NONE = 0;
        public static final int SHORTCUT_INVOCATION = 8;
        public static final int USER_INTERACTION = 7;
        
        public Event() {
            throw new RuntimeException("Stub!");
        }
        
        public String getPackageName() {
            throw new RuntimeException("Stub!");
        }
        
        public String getClassName() {
            throw new RuntimeException("Stub!");
        }
        
        public long getTimeStamp() {
            throw new RuntimeException("Stub!");
        }
        
        public int getEventType() {
            throw new RuntimeException("Stub!");
        }
        
        public Configuration getConfiguration() {
            throw new RuntimeException("Stub!");
        }
        
        public String getShortcutId() {
            throw new RuntimeException("Stub!");
        }
    }
}
