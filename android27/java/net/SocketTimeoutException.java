package java.net;

import java.io.*;

public class SocketTimeoutException extends InterruptedIOException
{
    private static final long serialVersionUID = -8846654841826352300L;
    
    public SocketTimeoutException(final String s) {
        super(s);
    }
    
    public SocketTimeoutException() {
    }
}
