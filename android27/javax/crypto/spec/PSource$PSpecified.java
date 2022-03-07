package javax.crypto.spec;

public static final class PSpecified extends PSource
{
    private byte[] p;
    public static final PSpecified DEFAULT;
    
    public PSpecified(final byte[] array) {
        super("PSpecified");
        this.p = new byte[0];
        this.p = array.clone();
    }
    
    public byte[] getValue() {
        return (this.p.length == 0) ? this.p : this.p.clone();
    }
    
    static {
        DEFAULT = new PSpecified(new byte[0]);
    }
}
