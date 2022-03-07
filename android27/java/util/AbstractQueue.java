package java.util;

public abstract class AbstractQueue<E> extends AbstractCollection<E> implements Queue<E>
{
    @Override
    public boolean add(final E e) {
        if (this.offer(e)) {
            return true;
        }
        throw new IllegalStateException("Queue full");
    }
    
    @Override
    public E remove() {
        final E poll = this.poll();
        if (poll != null) {
            return poll;
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public E element() {
        final E peek = this.peek();
        if (peek != null) {
            return peek;
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public void clear() {
        while (this.poll() != null) {}
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        boolean b = false;
        final Iterator<E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.add(iterator.next())) {
                b = true;
            }
        }
        return b;
    }
}
