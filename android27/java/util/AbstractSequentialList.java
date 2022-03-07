package java.util;

public abstract class AbstractSequentialList<E> extends AbstractList<E>
{
    @Override
    public E get(final int n) {
        try {
            return this.listIterator(n).next();
        }
        catch (NoSuchElementException ex) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
    }
    
    @Override
    public E set(final int n, final E e) {
        try {
            final ListIterator<E> listIterator = this.listIterator(n);
            final E next = listIterator.next();
            listIterator.set(e);
            return next;
        }
        catch (NoSuchElementException ex) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
    }
    
    @Override
    public void add(final int n, final E e) {
        try {
            this.listIterator(n).add(e);
        }
        catch (NoSuchElementException ex) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
    }
    
    @Override
    public E remove(final int n) {
        try {
            final ListIterator<E> listIterator = this.listIterator(n);
            final E next = listIterator.next();
            listIterator.remove();
            return next;
        }
        catch (NoSuchElementException ex) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
    }
    
    @Override
    public boolean addAll(final int n, final Collection<? extends E> collection) {
        try {
            boolean b = false;
            final ListIterator<Object> listIterator = this.listIterator(n);
            final Iterator<? extends E> iterator = collection.iterator();
            while (iterator.hasNext()) {
                listIterator.add(iterator.next());
                b = true;
            }
            return b;
        }
        catch (NoSuchElementException ex) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return this.listIterator();
    }
    
    @Override
    public abstract ListIterator<E> listIterator(final int p0);
}
