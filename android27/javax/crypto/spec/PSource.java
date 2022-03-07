package javax.crypto.spec;

public class PSource
{
    private String pSrcName;
    
    protected PSource(final String pSrcName) {
        if (pSrcName == null) {
            throw new NullPointerException("pSource algorithm is null");
        }
        this.pSrcName = pSrcName;
    }
    
    public String getAlgorithm() {
        return this.pSrcName;
    }
    
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
}
