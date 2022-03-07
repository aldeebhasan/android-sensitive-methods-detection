package android.net.wifi;

public static class GroupCipher
{
    public static final int CCMP = 3;
    public static final int TKIP = 2;
    public static final int WEP104 = 1;
    public static final int WEP40 = 0;
    public static final String[] strings;
    public static final String varName = "group";
    
    GroupCipher() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        strings = null;
    }
}
