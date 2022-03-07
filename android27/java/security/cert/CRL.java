package java.security.cert;

public abstract class CRL
{
    private String type;
    
    protected CRL(final String type) {
        this.type = type;
    }
    
    public final String getType() {
        return this.type;
    }
    
    @Override
    public abstract String toString();
    
    public abstract boolean isRevoked(final Certificate p0);
}
