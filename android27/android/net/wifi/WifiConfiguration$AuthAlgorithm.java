package android.net.wifi;

public static class AuthAlgorithm
{
    public static final int LEAP = 2;
    public static final int OPEN = 0;
    public static final int SHARED = 1;
    public static final String[] strings;
    public static final String varName = "auth_alg";
    
    AuthAlgorithm() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        strings = null;
    }
}
