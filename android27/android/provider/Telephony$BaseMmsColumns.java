package android.provider;

public interface BaseMmsColumns extends BaseColumns
{
    public static final String CONTENT_CLASS = "ct_cls";
    public static final String CONTENT_LOCATION = "ct_l";
    public static final String CONTENT_TYPE = "ct_t";
    public static final String CREATOR = "creator";
    public static final String DATE = "date";
    public static final String DATE_SENT = "date_sent";
    public static final String DELIVERY_REPORT = "d_rpt";
    public static final String DELIVERY_TIME = "d_tm";
    public static final String EXPIRY = "exp";
    public static final String LOCKED = "locked";
    public static final String MESSAGE_BOX = "msg_box";
    public static final int MESSAGE_BOX_ALL = 0;
    public static final int MESSAGE_BOX_DRAFTS = 3;
    public static final int MESSAGE_BOX_FAILED = 5;
    public static final int MESSAGE_BOX_INBOX = 1;
    public static final int MESSAGE_BOX_OUTBOX = 4;
    public static final int MESSAGE_BOX_SENT = 2;
    public static final String MESSAGE_CLASS = "m_cls";
    public static final String MESSAGE_ID = "m_id";
    public static final String MESSAGE_SIZE = "m_size";
    public static final String MESSAGE_TYPE = "m_type";
    public static final String MMS_VERSION = "v";
    public static final String PRIORITY = "pri";
    public static final String READ = "read";
    public static final String READ_REPORT = "rr";
    public static final String READ_STATUS = "read_status";
    public static final String REPORT_ALLOWED = "rpt_a";
    public static final String RESPONSE_STATUS = "resp_st";
    public static final String RESPONSE_TEXT = "resp_txt";
    public static final String RETRIEVE_STATUS = "retr_st";
    public static final String RETRIEVE_TEXT = "retr_txt";
    public static final String RETRIEVE_TEXT_CHARSET = "retr_txt_cs";
    public static final String SEEN = "seen";
    public static final String STATUS = "st";
    public static final String SUBJECT = "sub";
    public static final String SUBJECT_CHARSET = "sub_cs";
    public static final String SUBSCRIPTION_ID = "sub_id";
    public static final String TEXT_ONLY = "text_only";
    public static final String THREAD_ID = "thread_id";
    public static final String TRANSACTION_ID = "tr_id";
}
