package java.lang.reflect;

import java.security.*;

public final class ReflectPermission extends BasicPermission
{
    private static final long serialVersionUID = 7412737110241507485L;
    
    public ReflectPermission(final String s) {
        super(s);
    }
    
    public ReflectPermission(final String s, final String s2) {
        super(s, s2);
    }
}
