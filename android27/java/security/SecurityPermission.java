package java.security;

public final class SecurityPermission extends BasicPermission
{
    private static final long serialVersionUID = 5236109936224050470L;
    
    public SecurityPermission(final String s) {
        super(s);
    }
    
    public SecurityPermission(final String s, final String s2) {
        super(s, s2);
    }
}
