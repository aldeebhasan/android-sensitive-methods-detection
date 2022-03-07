package java.net;

import java.io.*;

public class SocketException extends IOException
{
    private static final long serialVersionUID = -5935874303556886934L;
    
    public SocketException(final String s) {
        super(s);
    }
    
    public SocketException() {
    }
}
