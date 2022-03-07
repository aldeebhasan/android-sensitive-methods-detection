package java.lang;

import java.security.*;

public final class RuntimePermission extends BasicPermission
{
    private static final long serialVersionUID = 7399184964622342223L;
    
    public RuntimePermission(final String s) {
        super(s);
    }
    
    public RuntimePermission(final String s, final String s2) {
        super(s, s2);
    }
}
