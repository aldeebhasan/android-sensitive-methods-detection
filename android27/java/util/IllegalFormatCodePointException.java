package java.util;

public class IllegalFormatCodePointException extends IllegalFormatException
{
    private static final long serialVersionUID = 19080630L;
    private int c;
    
    public IllegalFormatCodePointException(final int c) {
        this.c = c;
    }
    
    public int getCodePoint() {
        return this.c;
    }
    
    @Override
    public String getMessage() {
        return String.format("Code point = %#x", this.c);
    }
}
