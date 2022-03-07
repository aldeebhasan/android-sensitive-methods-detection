package java.nio.file;

public class FileAlreadyExistsException extends FileSystemException
{
    static final long serialVersionUID = 7579540934498831181L;
    
    public FileAlreadyExistsException(final String s) {
        super(s);
    }
    
    public FileAlreadyExistsException(final String s, final String s2, final String s3) {
        super(s, s2, s3);
    }
}
