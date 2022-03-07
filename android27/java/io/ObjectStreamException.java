package java.io;

public abstract class ObjectStreamException extends IOException
{
    private static final long serialVersionUID = 7260898174833392607L;
    
    protected ObjectStreamException(final String s) {
        super(s);
    }
    
    protected ObjectStreamException() {
    }
}
