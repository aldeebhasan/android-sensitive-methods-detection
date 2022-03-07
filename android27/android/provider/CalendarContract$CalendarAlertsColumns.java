package android.provider;

protected interface CalendarAlertsColumns
{
    public static final String ALARM_TIME = "alarmTime";
    public static final String BEGIN = "begin";
    public static final String CREATION_TIME = "creationTime";
    public static final String DEFAULT_SORT_ORDER = "begin ASC,title ASC";
    public static final String END = "end";
    public static final String EVENT_ID = "event_id";
    public static final String MINUTES = "minutes";
    public static final String NOTIFY_TIME = "notifyTime";
    public static final String RECEIVED_TIME = "receivedTime";
    public static final String STATE = "state";
    public static final int STATE_DISMISSED = 2;
    public static final int STATE_FIRED = 1;
    public static final int STATE_SCHEDULED = 0;
}
