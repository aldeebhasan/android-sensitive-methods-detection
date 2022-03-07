package java.nio.file;

public class AtomicMoveNotSupportedException extends FileSystemException
{
    static final long serialVersionUID = 5402760225333135579L;
    
    public AtomicMoveNotSupportedException(final String s, final String s2, final String s3) {
        super(s, s2, s3);
    }
}
