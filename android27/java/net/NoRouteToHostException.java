package java.net;

public class NoRouteToHostException extends SocketException
{
    private static final long serialVersionUID = -1897550894873493790L;
    
    public NoRouteToHostException(final String s) {
        super(s);
    }
    
    public NoRouteToHostException() {
    }
}
