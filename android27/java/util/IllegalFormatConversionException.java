package java.util;

public class IllegalFormatConversionException extends IllegalFormatException
{
    private static final long serialVersionUID = 17000126L;
    private char c;
    private Class<?> arg;
    
    public IllegalFormatConversionException(final char c, final Class<?> arg) {
        if (arg == null) {
            throw new NullPointerException();
        }
        this.c = c;
        this.arg = arg;
    }
    
    public char getConversion() {
        return this.c;
    }
    
    public Class<?> getArgumentClass() {
        return this.arg;
    }
    
    @Override
    public String getMessage() {
        return String.format("%c != %s", this.c, this.arg.getName());
    }
}
