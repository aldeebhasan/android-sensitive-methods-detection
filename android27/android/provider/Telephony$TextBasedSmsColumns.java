package android.provider;

public interface TextBasedSmsColumns
{
    public static final String ADDRESS = "address";
    public static final String BODY = "body";
    public static final String CREATOR = "creator";
    public static final String DATE = "date";
    public static final String DATE_SENT = "date_sent";
    public static final String ERROR_CODE = "error_code";
    public static final String LOCKED = "locked";
    public static final int MESSAGE_TYPE_ALL = 0;
    public static final int MESSAGE_TYPE_DRAFT = 3;
    public static final int MESSAGE_TYPE_FAILED = 5;
    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_OUTBOX = 4;
    public static final int MESSAGE_TYPE_QUEUED = 6;
    public static final int MESSAGE_TYPE_SENT = 2;
    public static final String PERSON = "person";
    public static final String PROTOCOL = "protocol";
    public static final String READ = "read";
    public static final String REPLY_PATH_PRESENT = "reply_path_present";
    public static final String SEEN = "seen";
    public static final String SERVICE_CENTER = "service_center";
    public static final String STATUS = "status";
    public static final int STATUS_COMPLETE = 0;
    public static final int STATUS_FAILED = 64;
    public static final int STATUS_NONE = -1;
    public static final int STATUS_PENDING = 32;
    public static final String SUBJECT = "subject";
    public static final String SUBSCRIPTION_ID = "sub_id";
    public static final String THREAD_ID = "thread_id";
    public static final String TYPE = "type";
}
