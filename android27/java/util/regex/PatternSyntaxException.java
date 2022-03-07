package java.util.regex;

import sun.security.action.*;
import java.security.*;

public class PatternSyntaxException extends IllegalArgumentException
{
    private static final long serialVersionUID = -3864639126226059218L;
    private final String desc;
    private final String pattern;
    private final int index;
    private static final String nl;
    
    public PatternSyntaxException(final String desc, final String pattern, final int index) {
        this.desc = desc;
        this.pattern = pattern;
        this.index = index;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getDescription() {
        return this.desc;
    }
    
    public String getPattern() {
        return this.pattern;
    }
    
    @Override
    public String getMessage() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.desc);
        if (this.index >= 0) {
            sb.append(" near index ");
            sb.append(this.index);
        }
        sb.append(PatternSyntaxException.nl);
        sb.append(this.pattern);
        if (this.index >= 0 && this.pattern != null && this.index < this.pattern.length()) {
            sb.append(PatternSyntaxException.nl);
            for (int i = 0; i < this.index; ++i) {
                sb.append(' ');
            }
            sb.append('^');
        }
        return sb.toString();
    }
    
    static {
        nl = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("line.separator"));
    }
}
