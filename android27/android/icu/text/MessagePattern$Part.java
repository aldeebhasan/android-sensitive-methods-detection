package android.icu.text;

public static final class Part
{
    Part() {
        throw new RuntimeException("Stub!");
    }
    
    public Type getType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIndex() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLength() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLimit() {
        throw new RuntimeException("Stub!");
    }
    
    public int getValue() {
        throw new RuntimeException("Stub!");
    }
    
    public ArgType getArgType() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object other) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
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
}
