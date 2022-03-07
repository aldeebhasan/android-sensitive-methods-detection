package java.text;

public abstract class CollationKey implements Comparable<CollationKey>
{
    private final String source;
    
    @Override
    public abstract int compareTo(final CollationKey p0);
    
    public String getSourceString() {
        return this.source;
    }
    
    public abstract byte[] toByteArray();
    
    protected CollationKey(final String source) {
        if (source == null) {
            throw new NullPointerException();
        }
        this.source = source;
    }
}
