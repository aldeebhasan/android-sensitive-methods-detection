package android.net;

public static class IllegalCharacterValueSanitizer implements ValueSanitizer
{
    public static final int ALL_BUT_NUL_AND_ANGLE_BRACKETS_LEGAL = 1439;
    public static final int ALL_BUT_NUL_LEGAL = 1535;
    public static final int ALL_BUT_WHITESPACE_LEGAL = 1532;
    public static final int ALL_ILLEGAL = 0;
    public static final int ALL_OK = 2047;
    public static final int ALL_WHITESPACE_OK = 3;
    public static final int AMP_AND_SPACE_LEGAL = 129;
    public static final int AMP_LEGAL = 128;
    public static final int AMP_OK = 128;
    public static final int DQUOTE_OK = 8;
    public static final int GT_OK = 64;
    public static final int LT_OK = 32;
    public static final int NON_7_BIT_ASCII_OK = 4;
    public static final int NUL_OK = 512;
    public static final int OTHER_WHITESPACE_OK = 2;
    public static final int PCT_OK = 256;
    public static final int SCRIPT_URL_OK = 1024;
    public static final int SPACE_LEGAL = 1;
    public static final int SPACE_OK = 1;
    public static final int SQUOTE_OK = 16;
    public static final int URL_AND_SPACE_LEGAL = 405;
    public static final int URL_LEGAL = 404;
    
    public IllegalCharacterValueSanitizer(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String sanitize(final String value) {
        throw new RuntimeException("Stub!");
    }
}
