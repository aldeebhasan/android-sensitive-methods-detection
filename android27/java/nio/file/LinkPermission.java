package java.nio.file;

import java.security.*;

public final class LinkPermission extends BasicPermission
{
    static final long serialVersionUID = -1441492453772213220L;
    
    private void checkName(final String s) {
        if (!s.equals("hard") && !s.equals("symbolic")) {
            throw new IllegalArgumentException("name: " + s);
        }
    }
    
    public LinkPermission(final String s) {
        super(s);
        this.checkName(s);
    }
    
    public LinkPermission(final String s, final String s2) {
        super(s);
        this.checkName(s);
        if (s2 != null && s2.length() > 0) {
            throw new IllegalArgumentException("actions: " + s2);
        }
    }
}
