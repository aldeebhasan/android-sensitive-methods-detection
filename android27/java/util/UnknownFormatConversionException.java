package java.util;

public class UnknownFormatConversionException extends IllegalFormatException
{
    private static final long serialVersionUID = 19060418L;
    private String s;
    
    public UnknownFormatConversionException(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        this.s = s;
    }
    
    public String getConversion() {
        return this.s;
    }
    
    @Override
    public String getMessage() {
        return String.format("Conversion = '%s'", this.s);
    }
}
