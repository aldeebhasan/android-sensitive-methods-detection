package java.io;

public class OptionalDataException extends ObjectStreamException
{
    private static final long serialVersionUID = -8011121865681257820L;
    public int length;
    public boolean eof;
    
    OptionalDataException(final int length) {
        this.eof = false;
        this.length = length;
    }
    
    OptionalDataException(final boolean eof) {
        this.length = 0;
        this.eof = eof;
    }
}
