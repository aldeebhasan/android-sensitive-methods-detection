package java.nio.file;

public class FileSystemLoopException extends FileSystemException
{
    private static final long serialVersionUID = 4843039591949217617L;
    
    public FileSystemLoopException(final String s) {
        super(s);
    }
}
