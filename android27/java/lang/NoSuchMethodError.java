package java.lang;

public class NoSuchMethodError extends IncompatibleClassChangeError
{
    private static final long serialVersionUID = -3765521442372831335L;
    
    public NoSuchMethodError() {
    }
    
    public NoSuchMethodError(final String s) {
        super(s);
    }
}
