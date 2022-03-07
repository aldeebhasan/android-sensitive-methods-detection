package javax.net.ssl;

import java.io.*;

public class SSLException extends IOException
{
    private static final long serialVersionUID = 4511006460650708967L;
    
    public SSLException(final String s) {
        super(s);
    }
    
    public SSLException(final String s, final Throwable t) {
        super(s);
        this.initCause(t);
    }
    
    public SSLException(final Throwable t) {
        super((t == null) ? null : t.toString());
        this.initCause(t);
    }
}
