package java.net;

import java.io.*;

public class UnknownServiceException extends IOException
{
    private static final long serialVersionUID = -4169033248853639508L;
    
    public UnknownServiceException() {
    }
    
    public UnknownServiceException(final String s) {
        super(s);
    }
}
