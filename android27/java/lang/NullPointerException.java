package java.lang;

public class NullPointerException extends RuntimeException
{
    private static final long serialVersionUID = 5162710183389028792L;
    
    public NullPointerException() {
    }
    
    public NullPointerException(final String s) {
        super(s);
    }
}
