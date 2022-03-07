package java.lang.invoke;

public class LambdaConversionException extends Exception
{
    private static final long serialVersionUID = 300L;
    
    public LambdaConversionException() {
    }
    
    public LambdaConversionException(final String s) {
        super(s);
    }
    
    public LambdaConversionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public LambdaConversionException(final Throwable t) {
        super(t);
    }
    
    public LambdaConversionException(final String s, final Throwable t, final boolean b, final boolean b2) {
        super(s, t, b, b2);
    }
}
