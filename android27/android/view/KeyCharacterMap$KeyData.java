package android.view;

@Deprecated
public static class KeyData
{
    public static final int META_LENGTH = 4;
    public char displayLabel;
    public char[] meta;
    public char number;
    
    public KeyData() {
        this.meta = null;
        throw new RuntimeException("Stub!");
    }
}
