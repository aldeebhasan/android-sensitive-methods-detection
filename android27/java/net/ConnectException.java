package java.net;

public class ConnectException extends SocketException
{
    private static final long serialVersionUID = 3831404271622369215L;
    
    public ConnectException(final String s) {
        super(s);
    }
    
    public ConnectException() {
    }
}
