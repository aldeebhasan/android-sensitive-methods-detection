package java.lang;

public class NoSuchFieldError extends IncompatibleClassChangeError
{
    private static final long serialVersionUID = -3456430195886129035L;
    
    public NoSuchFieldError() {
    }
    
    public NoSuchFieldError(final String s) {
        super(s);
    }
}
