package java.util;

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E>
{
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        final Collection collection = (Collection)o;
        if (collection.size() != this.size()) {
            return false;
        }
        try {
            return this.containsAll(collection);
        }
        catch (ClassCastException ex) {
            return false;
        }
        catch (NullPointerException ex2) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        for (final Object next : this) {
            if (next != null) {
                n += next.hashCode();
            }
        }
        return n;
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        Objects.requireNonNull(collection);
        boolean b = false;
        if (this.size() > collection.size()) {
            final Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                b |= this.remove(iterator.next());
            }
        }
        else {
            final Iterator<Object> iterator2 = (Iterator<Object>)this.iterator();
            while (iterator2.hasNext()) {
                if (collection.contains(iterator2.next())) {
                    iterator2.remove();
                    b = true;
                }
            }
        }
        return b;
    }
}
