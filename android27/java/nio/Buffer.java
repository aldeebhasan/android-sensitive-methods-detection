package java.nio;

public abstract class Buffer
{
    static final int SPLITERATOR_CHARACTERISTICS = 16464;
    private int mark;
    private int position;
    private int limit;
    private int capacity;
    long address;
    
    Buffer(final int mark, final int n, final int n2, final int capacity) {
        this.mark = -1;
        this.position = 0;
        if (capacity < 0) {
            throw new IllegalArgumentException("Negative capacity: " + capacity);
        }
        this.capacity = capacity;
        this.limit(n2);
        this.position(n);
        if (mark >= 0) {
            if (mark > n) {
                throw new IllegalArgumentException("mark > position: (" + mark + " > " + n + ")");
            }
            this.mark = mark;
        }
    }
    
    public final int capacity() {
        return this.capacity;
    }
    
    public final int position() {
        return this.position;
    }
    
    public final Buffer position(final int position) {
        if (position > this.limit || position < 0) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        if (this.mark > this.position) {
            this.mark = -1;
        }
        return this;
    }
    
    public final int limit() {
        return this.limit;
    }
    
    public final Buffer limit(final int n) {
        if (n > this.capacity || n < 0) {
            throw new IllegalArgumentException();
        }
        if (this.position > (this.limit = n)) {
            this.position = n;
        }
        if (this.mark > n) {
            this.mark = -1;
        }
        return this;
    }
    
    public final Buffer mark() {
        this.mark = this.position;
        return this;
    }
    
    public final Buffer reset() {
        final int mark = this.mark;
        if (mark < 0) {
            throw new InvalidMarkException();
        }
        this.position = mark;
        return this;
    }
    
    public final Buffer clear() {
        this.position = 0;
        this.limit = this.capacity;
        this.mark = -1;
        return this;
    }
    
    public final Buffer flip() {
        this.limit = this.position;
        this.position = 0;
        this.mark = -1;
        return this;
    }
    
    public final Buffer rewind() {
        this.position = 0;
        this.mark = -1;
        return this;
    }
    
    public final int remaining() {
        return this.limit - this.position;
    }
    
    public final boolean hasRemaining() {
        return this.position < this.limit;
    }
    
    public abstract boolean isReadOnly();
    
    public abstract boolean hasArray();
    
    public abstract Object array();
    
    public abstract int arrayOffset();
    
    public abstract boolean isDirect();
    
    final int nextGetIndex() {
        final int position = this.position;
        if (position >= this.limit) {
            throw new BufferUnderflowException();
        }
        this.position = position + 1;
        return position;
    }
    
    final int nextGetIndex(final int n) {
        final int position = this.position;
        if (this.limit - position < n) {
            throw new BufferUnderflowException();
        }
        this.position = position + n;
        return position;
    }
    
    final int nextPutIndex() {
        final int position = this.position;
        if (position >= this.limit) {
            throw new BufferOverflowException();
        }
        this.position = position + 1;
        return position;
    }
    
    final int nextPutIndex(final int n) {
        final int position = this.position;
        if (this.limit - position < n) {
            throw new BufferOverflowException();
        }
        this.position = position + n;
        return position;
    }
    
    final int checkIndex(final int n) {
        if (n < 0 || n >= this.limit) {
            throw new IndexOutOfBoundsException();
        }
        return n;
    }
    
    final int checkIndex(final int n, final int n2) {
        if (n < 0 || n2 > this.limit - n) {
            throw new IndexOutOfBoundsException();
        }
        return n;
    }
    
    final int markValue() {
        return this.mark;
    }
    
    final void truncate() {
        this.mark = -1;
        this.position = 0;
        this.limit = 0;
        this.capacity = 0;
    }
    
    final void discardMark() {
        this.mark = -1;
    }
    
    static void checkBounds(final int n, final int n2, final int n3) {
        if ((n | n2 | n + n2 | n3 - (n + n2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
