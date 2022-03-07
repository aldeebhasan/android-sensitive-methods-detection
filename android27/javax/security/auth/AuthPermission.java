package javax.security.auth;

import java.security.*;

public final class AuthPermission extends BasicPermission
{
    private static final long serialVersionUID = 5806031445061587174L;
    
    public AuthPermission(final String s) {
        super("createLoginContext".equals(s) ? "createLoginContext.*" : s);
    }
    
    public AuthPermission(final String s, final String s2) {
        super("createLoginContext".equals(s) ? "createLoginContext.*" : s, s2);
    }
}
