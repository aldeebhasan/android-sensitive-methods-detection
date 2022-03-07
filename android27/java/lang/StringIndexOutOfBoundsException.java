package java.lang;

public class StringIndexOutOfBoundsException extends IndexOutOfBoundsException
{
    private static final long serialVersionUID = -6762910422159637258L;
    
    public StringIndexOutOfBoundsException() {
    }
    
    public StringIndexOutOfBoundsException(final String s) {
        super(s);
    }
    
    public StringIndexOutOfBoundsException(final int n) {
        super("String index out of range: " + n);
    }
}
