package java.util;

public class MissingResourceException extends RuntimeException
{
    private static final long serialVersionUID = -4876345176062000401L;
    private String className;
    private String key;
    
    public MissingResourceException(final String s, final String className, final String key) {
        super(s);
        this.className = className;
        this.key = key;
    }
    
    MissingResourceException(final String s, final String className, final String key, final Throwable t) {
        super(s, t);
        this.className = className;
        this.key = key;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public String getKey() {
        return this.key;
    }
}
