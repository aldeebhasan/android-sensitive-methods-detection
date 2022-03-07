package android.provider;

protected interface CalendarColumns
{
    public static final String ALLOWED_ATTENDEE_TYPES = "allowedAttendeeTypes";
    public static final String ALLOWED_AVAILABILITY = "allowedAvailability";
    public static final String ALLOWED_REMINDERS = "allowedReminders";
    public static final String CALENDAR_ACCESS_LEVEL = "calendar_access_level";
    public static final String CALENDAR_COLOR = "calendar_color";
    public static final String CALENDAR_COLOR_KEY = "calendar_color_index";
    public static final String CALENDAR_DISPLAY_NAME = "calendar_displayName";
    public static final String CALENDAR_TIME_ZONE = "calendar_timezone";
    public static final int CAL_ACCESS_CONTRIBUTOR = 500;
    public static final int CAL_ACCESS_EDITOR = 600;
    public static final int CAL_ACCESS_FREEBUSY = 100;
    public static final int CAL_ACCESS_NONE = 0;
    public static final int CAL_ACCESS_OVERRIDE = 400;
    public static final int CAL_ACCESS_OWNER = 700;
    public static final int CAL_ACCESS_READ = 200;
    public static final int CAL_ACCESS_RESPOND = 300;
    public static final int CAL_ACCESS_ROOT = 800;
    public static final String CAN_MODIFY_TIME_ZONE = "canModifyTimeZone";
    public static final String CAN_ORGANIZER_RESPOND = "canOrganizerRespond";
    public static final String IS_PRIMARY = "isPrimary";
    public static final String MAX_REMINDERS = "maxReminders";
    public static final String OWNER_ACCOUNT = "ownerAccount";
    public static final String SYNC_EVENTS = "sync_events";
    public static final String VISIBLE = "visible";
}
