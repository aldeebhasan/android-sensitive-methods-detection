package java.util.logging;

import java.security.*;

public final class LoggingPermission extends BasicPermission
{
    private static final long serialVersionUID = 63564341580231582L;
    
    public LoggingPermission(final String s, final String s2) throws IllegalArgumentException {
        super(s);
        if (!s.equals("control")) {
            throw new IllegalArgumentException("name: " + s);
        }
        if (s2 != null && s2.length() > 0) {
            throw new IllegalArgumentException("actions: " + s2);
        }
    }
}
