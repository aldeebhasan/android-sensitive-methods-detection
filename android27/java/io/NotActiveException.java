package java.io;

public class NotActiveException extends ObjectStreamException
{
    private static final long serialVersionUID = -3893467273049808895L;
    
    public NotActiveException(final String s) {
        super(s);
    }
    
    public NotActiveException() {
    }
}
