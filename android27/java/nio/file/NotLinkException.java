package java.nio.file;

public class NotLinkException extends FileSystemException
{
    static final long serialVersionUID = -388655596416518021L;
    
    public NotLinkException(final String s) {
        super(s);
    }
    
    public NotLinkException(final String s, final String s2, final String s3) {
        super(s, s2, s3);
    }
}
