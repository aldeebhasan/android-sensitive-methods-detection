package java.util;

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>
{
    protected transient int modCount;
    
    protected AbstractList() {
        this.modCount = 0;
    }
    
    @Override
    public boolean add(final E e) {
        this.add(this.size(), e);
        return true;
    }
    
    @Override
    public abstract E get(final int p0);
    
    @Override
    public E set(final int n, final E e) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void add(final int n, final E e) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public E remove(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int indexOf(final Object o) {
        final ListIterator<Object> listIterator = (ListIterator<Object>)this.listIterator();
        if (o == null) {
            while (listIterator.hasNext()) {
                if (listIterator.next() == null) {
                    return listIterator.previousIndex();
                }
            }
        }
        else {
            while (listIterator.hasNext()) {
                if (o.equals(listIterator.next())) {
                    return listIterator.previousIndex();
                }
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        final ListIterator<Object> listIterator = (ListIterator<Object>)this.listIterator(this.size());
        if (o == null) {
            while (listIterator.hasPrevious()) {
                if (listIterator.previous() == null) {
                    return listIterator.nextIndex();
                }
            }
        }
        else {
            while (listIterator.hasPrevious()) {
                if (o.equals(listIterator.previous())) {
                    return listIterator.nextIndex();
                }
            }
        }
        return -1;
    }
    
    @Override
    public void clear() {
        this.removeRange(0, this.size());
    }
    
    @Override
    public boolean addAll(int n, final Collection<? extends E> collection) {
        this.rangeCheckForAdd(n);
        boolean b = false;
        final Iterator<? extends E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.add(n++, iterator.next());
            b = true;
        }
        return b;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public ListIterator<E> listIterator(final int n) {
        this.rangeCheckForAdd(n);
        return new ListItr(n);
    }
    
    @Override
    public List<E> subList(final int n, final int n2) {
        return (this instanceof RandomAccess) ? new RandomAccessSubList<E>(this, n, n2) : new SubList<E>(this, n, n2);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        final ListIterator<Object> listIterator = (ListIterator<Object>)this.listIterator();
        final ListIterator<Object> listIterator2 = (ListIterator<Object>)((List)o).listIterator();
        while (listIterator.hasNext() && listIterator2.hasNext()) {
            final Object next = listIterator.next();
            final Object next2 = listIterator2.next();
            if (next == null) {
                if (next2 == null) {
                    continue;
                }
                return false;
            }
            else {
                if (!next.equals(next2)) {
                    return false;
                }
                continue;
            }
        }
        return !listIterator.hasNext() && !listIterator2.hasNext();
    }
    
    @Override
    public int hashCode() {
        int n = 1;
        for (final Object next : this) {
            n = 31 * n + ((next == null) ? 0 : next.hashCode());
        }
        return n;
    }
    
    protected void removeRange(final int n, final int n2) {
        final ListIterator<E> listIterator = this.listIterator(n);
        for (int i = 0; i < n2 - n; ++i) {
            listIterator.next();
            listIterator.remove();
        }
    }
    
    private void rangeCheckForAdd(final int n) {
        if (n < 0 || n > this.size()) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }
    }
    
    private String outOfBoundsMsg(final int n) {
        return "Index: " + n + ", Size: " + this.size();
    }
    
    private class Itr implements Iterator<E>
    {
        int cursor;
        int lastRet;
        int expectedModCount;
        
        private Itr() {
            this.cursor = 0;
            this.lastRet = -1;
            this.expectedModCount = AbstractList.this.modCount;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor != AbstractList.this.size();
        }
        
        @Override
        public E next() {
            this.checkForComodification();
            try {
                final int cursor = this.cursor;
                final E value = AbstractList.this.get(cursor);
                this.lastRet = cursor;
                this.cursor = cursor + 1;
                return value;
            }
            catch (IndexOutOfBoundsException ex) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
        }
        
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                AbstractList.this.remove(this.lastRet);
                if (this.lastRet < this.cursor) {
                    --this.cursor;
                }
                this.lastRet = -1;
                this.expectedModCount = AbstractList.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        
        final void checkForComodification() {
            if (AbstractList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    private class ListItr extends Itr implements ListIterator<E>
    {
        ListItr(final int cursor) {
            this.cursor = cursor;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.cursor != 0;
        }
        
        @Override
        public E previous() {
            this.checkForComodification();
            try {
                final int n = this.cursor - 1;
                final E value = AbstractList.this.get(n);
                final int n2 = n;
                this.cursor = n2;
                this.lastRet = n2;
                return value;
            }
            catch (IndexOutOfBoundsException ex) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
        }
        
        @Override
        public int nextIndex() {
            return this.cursor;
        }
        
        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }
        
        @Override
        public void set(final E e) {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                AbstractList.this.set(this.lastRet, e);
                this.expectedModCount = AbstractList.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public void add(final E e) {
            this.checkForComodification();
            try {
                final int cursor = this.cursor;
                AbstractList.this.add(cursor, e);
                this.lastRet = -1;
                this.cursor = cursor + 1;
                this.expectedModCount = AbstractList.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
