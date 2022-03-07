package java.net;

public class BindException extends SocketException
{
    private static final long serialVersionUID = -5945005768251722951L;
    
    public BindException(final String s) {
        super(s);
    }
    
    public BindException() {
    }
}
