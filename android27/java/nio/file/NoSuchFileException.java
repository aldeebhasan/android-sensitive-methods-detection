package java.nio.file;

public class NoSuchFileException extends FileSystemException
{
    static final long serialVersionUID = -1390291775875351931L;
    
    public NoSuchFileException(final String s) {
        super(s);
    }
    
    public NoSuchFileException(final String s, final String s2, final String s3) {
        super(s, s2, s3);
    }
}
