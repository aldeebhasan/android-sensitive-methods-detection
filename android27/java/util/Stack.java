package java.util;

public class Stack<E> extends Vector<E>
{
    private static final long serialVersionUID = 1224463164541339165L;
    
    public E push(final E e) {
        this.addElement(e);
        return e;
    }
    
    public synchronized E pop() {
        final int size = this.size();
        final E peek = this.peek();
        this.removeElementAt(size - 1);
        return peek;
    }
    
    public synchronized E peek() {
        final int size = this.size();
        if (size == 0) {
            throw new EmptyStackException();
        }
        return this.elementAt(size - 1);
    }
    
    public boolean empty() {
        return this.size() == 0;
    }
    
    public synchronized int search(final Object o) {
        final int lastIndex = this.lastIndexOf(o);
        if (lastIndex >= 0) {
            return this.size() - lastIndex;
        }
        return -1;
    }
}
