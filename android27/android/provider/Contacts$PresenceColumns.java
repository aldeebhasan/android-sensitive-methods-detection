package android.provider;

@Deprecated
public interface PresenceColumns
{
    public static final int AVAILABLE = 5;
    public static final int AWAY = 2;
    public static final int DO_NOT_DISTURB = 4;
    public static final int IDLE = 3;
    @Deprecated
    public static final String IM_ACCOUNT = "im_account";
    @Deprecated
    public static final String IM_HANDLE = "im_handle";
    @Deprecated
    public static final String IM_PROTOCOL = "im_protocol";
    public static final int INVISIBLE = 1;
    public static final int OFFLINE = 0;
    public static final String PRESENCE_CUSTOM_STATUS = "status";
    public static final String PRESENCE_STATUS = "mode";
    public static final String PRIORITY = "priority";
}
