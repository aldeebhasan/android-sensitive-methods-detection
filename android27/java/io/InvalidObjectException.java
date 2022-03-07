package java.io;

public class InvalidObjectException extends ObjectStreamException
{
    private static final long serialVersionUID = 3233174318281839583L;
    
    public InvalidObjectException(final String s) {
        super(s);
    }
}
