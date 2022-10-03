package java.lang.reflect;

public class GenericSignatureFormatError extends ClassFormatError
{
    private static final long serialVersionUID = 6709919147137911034L;
    
    public GenericSignatureFormatError() {
    }
    
    public GenericSignatureFormatError(final String s) {
        super(s);
    }
}
