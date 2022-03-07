package android.app;

public class AppOpsManager
{
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_IGNORED = 1;
    public static final String OPSTR_ADD_VOICEMAIL = "android:add_voicemail";
    public static final String OPSTR_ANSWER_PHONE_CALLS = "android:answer_phone_calls";
    public static final String OPSTR_BODY_SENSORS = "android:body_sensors";
    public static final String OPSTR_CALL_PHONE = "android:call_phone";
    public static final String OPSTR_CAMERA = "android:camera";
    public static final String OPSTR_COARSE_LOCATION = "android:coarse_location";
    public static final String OPSTR_FINE_LOCATION = "android:fine_location";
    public static final String OPSTR_GET_USAGE_STATS = "android:get_usage_stats";
    public static final String OPSTR_MOCK_LOCATION = "android:mock_location";
    public static final String OPSTR_MONITOR_HIGH_POWER_LOCATION = "android:monitor_location_high_power";
    public static final String OPSTR_MONITOR_LOCATION = "android:monitor_location";
    public static final String OPSTR_PICTURE_IN_PICTURE = "android:picture_in_picture";
    public static final String OPSTR_PROCESS_OUTGOING_CALLS = "android:process_outgoing_calls";
    public static final String OPSTR_READ_CALENDAR = "android:read_calendar";
    public static final String OPSTR_READ_CALL_LOG = "android:read_call_log";
    public static final String OPSTR_READ_CELL_BROADCASTS = "android:read_cell_broadcasts";
    public static final String OPSTR_READ_CONTACTS = "android:read_contacts";
    public static final String OPSTR_READ_EXTERNAL_STORAGE = "android:read_external_storage";
    public static final String OPSTR_READ_PHONE_NUMBERS = "android:read_phone_numbers";
    public static final String OPSTR_READ_PHONE_STATE = "android:read_phone_state";
    public static final String OPSTR_READ_SMS = "android:read_sms";
    public static final String OPSTR_RECEIVE_MMS = "android:receive_mms";
    public static final String OPSTR_RECEIVE_SMS = "android:receive_sms";
    public static final String OPSTR_RECEIVE_WAP_PUSH = "android:receive_wap_push";
    public static final String OPSTR_RECORD_AUDIO = "android:record_audio";
    public static final String OPSTR_SEND_SMS = "android:send_sms";
    public static final String OPSTR_SYSTEM_ALERT_WINDOW = "android:system_alert_window";
    public static final String OPSTR_USE_FINGERPRINT = "android:use_fingerprint";
    public static final String OPSTR_USE_SIP = "android:use_sip";
    public static final String OPSTR_WRITE_CALENDAR = "android:write_calendar";
    public static final String OPSTR_WRITE_CALL_LOG = "android:write_call_log";
    public static final String OPSTR_WRITE_CONTACTS = "android:write_contacts";
    public static final String OPSTR_WRITE_EXTERNAL_STORAGE = "android:write_external_storage";
    public static final String OPSTR_WRITE_SETTINGS = "android:write_settings";
    
    AppOpsManager() {
        throw new RuntimeException("Stub!");
    }
    
    public static String permissionToOp(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public void startWatchingMode(final String op, final String packageName, final OnOpChangedListener callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void stopWatchingMode(final OnOpChangedListener callback) {
        throw new RuntimeException("Stub!");
    }
    
    public int checkOp(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int checkOpNoThrow(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int noteOp(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int noteOpNoThrow(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int noteProxyOp(final String op, final String proxiedPackageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int noteProxyOpNoThrow(final String op, final String proxiedPackageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int startOp(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int startOpNoThrow(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void finishOp(final String op, final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void checkPackage(final int uid, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnOpChangedListener
    {
        void onOpChanged(final String p0, final String p1);
    }
}
