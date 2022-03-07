package android.icu.text;

public enum Type
{
    ARG_DOUBLE, 
    ARG_INT, 
    ARG_LIMIT, 
    ARG_NAME, 
    ARG_NUMBER, 
    ARG_SELECTOR, 
    ARG_START, 
    ARG_STYLE, 
    ARG_TYPE, 
    INSERT_CHAR, 
    MSG_LIMIT, 
    MSG_START, 
    REPLACE_NUMBER, 
    SKIP_SYNTAX;
    
    public boolean hasNumericValue() {
        throw new RuntimeException("Stub!");
    }
}
