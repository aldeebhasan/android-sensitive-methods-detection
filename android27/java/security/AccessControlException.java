package java.security;

public class AccessControlException extends SecurityException
{
    private static final long serialVersionUID = 5138225684096988535L;
    private Permission perm;
    
    public AccessControlException(final String s) {
        super(s);
    }
    
    public AccessControlException(final String s, final Permission perm) {
        super(s);
        this.perm = perm;
    }
    
    public Permission getPermission() {
        return this.perm;
    }
}
