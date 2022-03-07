package javax.net.ssl;

public abstract class SNIMatcher
{
    private final int type;
    
    protected SNIMatcher(final int type) {
        if (type < 0) {
            throw new IllegalArgumentException("Server name type cannot be less than zero");
        }
        if (type > 255) {
            throw new IllegalArgumentException("Server name type cannot be greater than 255");
        }
        this.type = type;
    }
    
    public final int getType() {
        return this.type;
    }
    
    public abstract boolean matches(final SNIServerName p0);
}
