package android.net.wifi;

public static class Status
{
    public static final int CURRENT = 0;
    public static final int DISABLED = 1;
    public static final int ENABLED = 2;
    public static final String[] strings;
    
    Status() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        strings = null;
    }
}
