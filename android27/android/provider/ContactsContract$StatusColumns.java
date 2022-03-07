package android.provider;

protected interface StatusColumns
{
    public static final int AVAILABLE = 5;
    public static final int AWAY = 2;
    public static final int CAPABILITY_HAS_CAMERA = 4;
    public static final int CAPABILITY_HAS_VIDEO = 2;
    public static final int CAPABILITY_HAS_VOICE = 1;
    public static final String CHAT_CAPABILITY = "chat_capability";
    public static final int DO_NOT_DISTURB = 4;
    public static final int IDLE = 3;
    public static final int INVISIBLE = 1;
    public static final int OFFLINE = 0;
    public static final String PRESENCE = "mode";
    @Deprecated
    public static final String PRESENCE_CUSTOM_STATUS = "status";
    @Deprecated
    public static final String PRESENCE_STATUS = "mode";
    public static final String STATUS = "status";
    public static final String STATUS_ICON = "status_icon";
    public static final String STATUS_LABEL = "status_label";
    public static final String STATUS_RES_PACKAGE = "status_res_package";
    public static final String STATUS_TIMESTAMP = "status_ts";
}
