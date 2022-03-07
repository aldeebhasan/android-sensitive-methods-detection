package java.security;

import java.util.*;

@Deprecated
public abstract class IdentityScope extends Identity
{
    private static final long serialVersionUID = -2337346281189773310L;
    private static IdentityScope scope;
    
    private static void initializeSystemScope() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("system.scope");
            }
        });
        if (s == null) {
            return;
        }
        try {
            Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            System.err.println("unable to establish a system scope from " + s);
            ex.printStackTrace();
        }
    }
    
    protected IdentityScope() {
        this("restoring...");
    }
    
    public IdentityScope(final String s) {
        super(s);
    }
    
    public IdentityScope(final String s, final IdentityScope identityScope) throws KeyManagementException {
        super(s, identityScope);
    }
    
    public static IdentityScope getSystemScope() {
        if (IdentityScope.scope == null) {
            initializeSystemScope();
        }
        return IdentityScope.scope;
    }
    
    protected static void setSystemScope(final IdentityScope scope) {
        check("setSystemScope");
        IdentityScope.scope = scope;
    }
    
    public abstract int size();
    
    public abstract Identity getIdentity(final String p0);
    
    public Identity getIdentity(final Principal principal) {
        return this.getIdentity(principal.getName());
    }
    
    public abstract Identity getIdentity(final PublicKey p0);
    
    public abstract void addIdentity(final Identity p0) throws KeyManagementException;
    
    public abstract void removeIdentity(final Identity p0) throws KeyManagementException;
    
    public abstract Enumeration<Identity> identities();
    
    @Override
    public String toString() {
        return super.toString() + "[" + this.size() + "]";
    }
    
    private static void check(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(s);
        }
    }
}
