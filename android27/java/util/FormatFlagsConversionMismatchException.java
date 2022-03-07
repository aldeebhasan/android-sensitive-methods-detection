package java.util;

public class FormatFlagsConversionMismatchException extends IllegalFormatException
{
    private static final long serialVersionUID = 19120414L;
    private String f;
    private char c;
    
    public FormatFlagsConversionMismatchException(final String f, final char c) {
        if (f == null) {
            throw new NullPointerException();
        }
        this.f = f;
        this.c = c;
    }
    
    public String getFlags() {
        return this.f;
    }
    
    public char getConversion() {
        return this.c;
    }
    
    @Override
    public String getMessage() {
        return "Conversion = " + this.c + ", Flags = " + this.f;
    }
}
