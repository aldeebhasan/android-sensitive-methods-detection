package android.text;

@Deprecated
public class AndroidCharacter
{
    public static final int EAST_ASIAN_WIDTH_AMBIGUOUS = 1;
    public static final int EAST_ASIAN_WIDTH_FULL_WIDTH = 3;
    public static final int EAST_ASIAN_WIDTH_HALF_WIDTH = 2;
    public static final int EAST_ASIAN_WIDTH_NARROW = 4;
    public static final int EAST_ASIAN_WIDTH_NEUTRAL = 0;
    public static final int EAST_ASIAN_WIDTH_WIDE = 5;
    
    public AndroidCharacter() {
        throw new RuntimeException("Stub!");
    }
    
    public static native void getDirectionalities(final char[] p0, final byte[] p1, final int p2);
    
    public static native int getEastAsianWidth(final char p0);
    
    public static native void getEastAsianWidths(final char[] p0, final int p1, final int p2, final byte[] p3);
    
    public static native boolean mirror(final char[] p0, final int p1, final int p2);
    
    public static native char getMirror(final char p0);
}
