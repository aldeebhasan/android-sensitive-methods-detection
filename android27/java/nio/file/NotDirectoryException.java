package java.nio.file;

public class NotDirectoryException extends FileSystemException
{
    private static final long serialVersionUID = -9011457427178200199L;
    
    public NotDirectoryException(final String s) {
        super(s);
    }
}
