package java.util.jar;

import java.util.zip.*;

public class JarException extends ZipException
{
    private static final long serialVersionUID = 7159778400963954473L;
    
    public JarException() {
    }
    
    public JarException(final String s) {
        super(s);
    }
}
