package android.icu.text;

public enum DisplayContext
{
    CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE, 
    CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE, 
    CAPITALIZATION_FOR_STANDALONE, 
    CAPITALIZATION_FOR_UI_LIST_OR_MENU, 
    CAPITALIZATION_NONE, 
    DIALECT_NAMES, 
    LENGTH_FULL, 
    LENGTH_SHORT, 
    STANDARD_NAMES;
    
    public Type type() {
        throw new RuntimeException("Stub!");
    }
    
    public int value() {
        throw new RuntimeException("Stub!");
    }
    
    public enum Type
    {
        CAPITALIZATION, 
        DIALECT_HANDLING, 
        DISPLAY_LENGTH;
    }
}
