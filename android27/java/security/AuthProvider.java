package java.security;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;

public abstract class AuthProvider extends Provider
{
    private static final long serialVersionUID = 4197859053084546461L;
    
    protected AuthProvider(final String s, final double n, final String s2) {
        super(s, n, s2);
    }
    
    public abstract void login(final Subject p0, final CallbackHandler p1) throws LoginException;
    
    public abstract void logout() throws LoginException;
    
    public abstract void setCallbackHandler(final CallbackHandler p0);
}
