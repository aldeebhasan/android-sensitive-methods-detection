package java.security;

import java.io.*;

public abstract class Permission implements Guard, Serializable
{
    private static final long serialVersionUID = -5636570222231596674L;
    private String name;
    
    public Permission(final String name) {
        this.name = name;
    }
    
    @Override
    public void checkGuard(final Object o) throws SecurityException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(this);
        }
    }
    
    public abstract boolean implies(final Permission p0);
    
    @Override
    public abstract boolean equals(final Object p0);
    
    @Override
    public abstract int hashCode();
    
    public final String getName() {
        return this.name;
    }
    
    public abstract String getActions();
    
    public PermissionCollection newPermissionCollection() {
        return null;
    }
    
    @Override
    public String toString() {
        final String actions = this.getActions();
        if (actions == null || actions.length() == 0) {
            return "(\"" + this.getClass().getName() + "\" \"" + this.name + "\")";
        }
        return "(\"" + this.getClass().getName() + "\" \"" + this.name + "\" \"" + actions + "\")";
    }
}
