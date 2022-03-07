package java.net;

import java.security.*;

public final class NetPermission extends BasicPermission
{
    private static final long serialVersionUID = -8343910153355041693L;
    
    public NetPermission(final String s) {
        super(s);
    }
    
    public NetPermission(final String s, final String s2) {
        super(s, s2);
    }
}
