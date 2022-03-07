package java.net;

import java.io.*;

public class ProtocolException extends IOException
{
    private static final long serialVersionUID = -6098449442062388080L;
    
    public ProtocolException(final String s) {
        super(s);
    }
    
    public ProtocolException() {
    }
}
