package java.io;

public class InterruptedIOException extends IOException
{
    private static final long serialVersionUID = 4020568460727500567L;
    public int bytesTransferred;
    
    public InterruptedIOException() {
        this.bytesTransferred = 0;
    }
    
    public InterruptedIOException(final String s) {
        super(s);
        this.bytesTransferred = 0;
    }
}
