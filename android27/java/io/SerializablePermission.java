package java.io;

import java.security.*;

public final class SerializablePermission extends BasicPermission
{
    private static final long serialVersionUID = 8537212141160296410L;
    private String actions;
    
    public SerializablePermission(final String s) {
        super(s);
    }
    
    public SerializablePermission(final String s, final String s2) {
        super(s, s2);
    }
}
