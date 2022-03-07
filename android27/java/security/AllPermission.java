package java.security;

public final class AllPermission extends Permission
{
    private static final long serialVersionUID = -2916474571451318075L;
    
    public AllPermission() {
        super("<all permissions>");
    }
    
    public AllPermission(final String s, final String s2) {
        this();
    }
    
    @Override
    public boolean implies(final Permission permission) {
        return true;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof AllPermission;
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
    
    @Override
    public String getActions() {
        return "<all actions>";
    }
    
    @Override
    public PermissionCollection newPermissionCollection() {
        return new AllPermissionCollection();
    }
}
