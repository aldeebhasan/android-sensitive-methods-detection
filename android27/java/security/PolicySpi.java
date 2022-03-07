package java.security;

public abstract class PolicySpi
{
    protected abstract boolean engineImplies(final ProtectionDomain p0, final Permission p1);
    
    protected void engineRefresh() {
    }
    
    protected PermissionCollection engineGetPermissions(final CodeSource codeSource) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }
    
    protected PermissionCollection engineGetPermissions(final ProtectionDomain protectionDomain) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }
}
