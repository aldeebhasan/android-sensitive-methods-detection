package java.io;

public class CharConversionException extends IOException
{
    private static final long serialVersionUID = -8680016352018427031L;
    
    public CharConversionException() {
    }
    
    public CharConversionException(final String s) {
        super(s);
    }
}
