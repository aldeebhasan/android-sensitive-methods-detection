package java.lang;

public class ClassCastException extends RuntimeException
{
    private static final long serialVersionUID = -9223365651070458532L;
    
    public ClassCastException() {
    }
    
    public ClassCastException(final String s) {
        super(s);
    }
}
