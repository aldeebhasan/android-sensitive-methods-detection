package java.security.cert;

import java.util.*;

public class CollectionCertStoreParameters implements CertStoreParameters
{
    private Collection<?> coll;
    
    public CollectionCertStoreParameters(final Collection<?> coll) {
        if (coll == null) {
            throw new NullPointerException();
        }
        this.coll = coll;
    }
    
    public CollectionCertStoreParameters() {
        this.coll = (Collection<?>)Collections.EMPTY_SET;
    }
    
    public Collection<?> getCollection() {
        return this.coll;
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("CollectionCertStoreParameters: [\n");
        sb.append("  collection: " + this.coll + "\n");
        sb.append("]");
        return sb.toString();
    }
}
