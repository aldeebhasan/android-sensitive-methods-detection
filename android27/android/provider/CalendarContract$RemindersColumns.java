package android.provider;

protected interface RemindersColumns
{
    public static final String EVENT_ID = "event_id";
    public static final String METHOD = "method";
    public static final int METHOD_ALARM = 4;
    public static final int METHOD_ALERT = 1;
    public static final int METHOD_DEFAULT = 0;
    public static final int METHOD_EMAIL = 2;
    public static final int METHOD_SMS = 3;
    public static final String MINUTES = "minutes";
    public static final int MINUTES_DEFAULT = -1;
}
