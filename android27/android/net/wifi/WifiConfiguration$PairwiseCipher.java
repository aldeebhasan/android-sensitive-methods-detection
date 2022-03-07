package android.net.wifi;

public static class PairwiseCipher
{
    public static final int CCMP = 2;
    public static final int NONE = 0;
    public static final int TKIP = 1;
    public static final String[] strings;
    public static final String varName = "pairwise";
    
    PairwiseCipher() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        strings = null;
    }
}
