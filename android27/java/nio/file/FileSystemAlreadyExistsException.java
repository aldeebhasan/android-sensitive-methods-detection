package java.nio.file;

public class FileSystemAlreadyExistsException extends RuntimeException
{
    static final long serialVersionUID = -5438419127181131148L;
    
    public FileSystemAlreadyExistsException() {
    }
    
    public FileSystemAlreadyExistsException(final String s) {
        super(s);
    }
}
