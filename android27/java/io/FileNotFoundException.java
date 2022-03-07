package java.io;

public class FileNotFoundException extends IOException
{
    private static final long serialVersionUID = -897856973823710492L;
    
    public FileNotFoundException() {
    }
    
    public FileNotFoundException(final String s) {
        super(s);
    }
    
    private FileNotFoundException(final String s, final String s2) {
        super(s + ((s2 == null) ? "" : (" (" + s2 + ")")));
    }
}
