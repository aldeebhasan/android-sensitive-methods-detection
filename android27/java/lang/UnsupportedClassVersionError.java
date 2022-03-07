package java.lang;

public class UnsupportedClassVersionError extends ClassFormatError
{
    private static final long serialVersionUID = -7123279212883497373L;
    
    public UnsupportedClassVersionError() {
    }
    
    public UnsupportedClassVersionError(final String s) {
        super(s);
    }
}
