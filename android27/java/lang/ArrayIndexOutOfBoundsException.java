package java.lang;

public class ArrayIndexOutOfBoundsException extends IndexOutOfBoundsException
{
    private static final long serialVersionUID = -5116101128118950844L;
    
    public ArrayIndexOutOfBoundsException() {
    }
    
    public ArrayIndexOutOfBoundsException(final int n) {
        super("Array index out of range: " + n);
    }
    
    public ArrayIndexOutOfBoundsException(final String s) {
        super(s);
    }
}
