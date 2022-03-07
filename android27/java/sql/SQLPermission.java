package java.sql;

import java.security.*;

public final class SQLPermission extends BasicPermission
{
    static final long serialVersionUID = -1439323187199563495L;
    
    public SQLPermission(final String s) {
        super(s);
    }
    
    public SQLPermission(final String s, final String s2) {
        super(s, s2);
    }
}
