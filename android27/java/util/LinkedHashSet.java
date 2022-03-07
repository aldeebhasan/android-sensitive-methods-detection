package java.util;

import java.io.*;

public class LinkedHashSet<E> extends HashSet<E> implements Set<E>, Cloneable, Serializable
{
    private static final long serialVersionUID = -2851667679971038690L;
    
    public LinkedHashSet(final int n, final float n2) {
        super(n, n2, true);
    }
    
    public LinkedHashSet(final int n) {
        super(n, 0.75f, true);
    }
    
    public LinkedHashSet() {
        super(16, 0.75f, true);
    }
    
    public LinkedHashSet(final Collection<? extends E> collection) {
        super(Math.max(2 * collection.size(), 11), 0.75f, true);
        this.addAll(collection);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator((Collection<? extends E>)this, 17);
    }
}
