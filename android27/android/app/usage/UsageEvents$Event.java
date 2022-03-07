package android.app.usage;

import android.content.res.*;

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
