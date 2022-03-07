package android.net.wifi;

public static class Protocol
{
    public static final int RSN = 1;
    public static final int WPA = 0;
    public static final String[] strings;
    public static final String varName = "proto";
    
    Protocol() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        strings = null;
    }
}
