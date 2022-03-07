package android.icu.text;

public enum ArgType
{
    CHOICE, 
    NONE, 
    PLURAL, 
    SELECT, 
    SELECTORDINAL, 
    SIMPLE;
    
    public boolean hasPluralStyle() {
        throw new RuntimeException("Stub!");
    }
}
