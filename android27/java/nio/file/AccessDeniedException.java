package java.nio.file;

public class AccessDeniedException extends FileSystemException
{
    private static final long serialVersionUID = 4943049599949219617L;
    
    public AccessDeniedException(final String s) {
        super(s);
    }
    
    public AccessDeniedException(final String s, final String s2, final String s3) {
        super(s, s2, s3);
    }
}
