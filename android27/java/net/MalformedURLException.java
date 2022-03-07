package java.net;

import java.io.*;

public class MalformedURLException extends IOException
{
    private static final long serialVersionUID = -182787522200415866L;
    
    public MalformedURLException() {
    }
    
    public MalformedURLException(final String s) {
        super(s);
    }
}
