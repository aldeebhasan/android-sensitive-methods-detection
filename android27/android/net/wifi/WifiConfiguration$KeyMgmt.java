package android.net.wifi;

public static class KeyMgmt
{
    public static final int IEEE8021X = 3;
    public static final int NONE = 0;
    public static final int WPA_EAP = 2;
    public static final int WPA_PSK = 1;
    public static final String[] strings;
    public static final String varName = "key_mgmt";
    
    KeyMgmt() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        strings = null;
    }
}
