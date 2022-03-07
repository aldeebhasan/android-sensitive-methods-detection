package android.icu.text;

public static final class PatternInfo
{
    public static final int BASE_CONFLICT = 1;
    public static final int CONFLICT = 2;
    public static final int OK = 0;
    public String conflictingPattern;
    public int status;
    
    public PatternInfo() {
        throw new RuntimeException("Stub!");
    }
}
