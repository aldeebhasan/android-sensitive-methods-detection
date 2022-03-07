package android.app.admin;

import android.os.*;

public class SecurityLog
{
    public static final int TAG_ADB_SHELL_CMD = 210002;
    public static final int TAG_ADB_SHELL_INTERACTIVE = 210001;
    public static final int TAG_APP_PROCESS_START = 210005;
    public static final int TAG_KEYGUARD_DISMISSED = 210006;
    public static final int TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT = 210007;
    public static final int TAG_KEYGUARD_SECURED = 210008;
    public static final int TAG_SYNC_RECV_FILE = 210003;
    public static final int TAG_SYNC_SEND_FILE = 210004;
    
    public SecurityLog() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class SecurityEvent implements Parcelable
    {
        public static final Creator<SecurityEvent> CREATOR;
        
        SecurityEvent() {
            throw new RuntimeException("Stub!");
        }
        
        public long getTimeNanos() {
            throw new RuntimeException("Stub!");
        }
        
        public int getTag() {
            throw new RuntimeException("Stub!");
        }
        
        public Object getData() {
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
    }
}
