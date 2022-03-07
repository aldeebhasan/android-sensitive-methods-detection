package javax.net.ssl;

import java.security.*;

public final class SSLPermission extends BasicPermission
{
    private static final long serialVersionUID = -3456898025505876775L;
    
    public SSLPermission(final String s) {
        super(s);
    }
    
    public SSLPermission(final String s, final String s2) {
        super(s, s2);
    }
}
