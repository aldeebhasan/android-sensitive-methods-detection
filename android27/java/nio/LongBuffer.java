package java.nio;

public abstract class LongBuffer extends Buffer implements Comparable<LongBuffer>
{
    final long[] hb;
    final int offset;
    boolean isReadOnly;
    
    LongBuffer(final int n, final int n2, final int n3, final int n4, final long[] hb, final int offset) {
        super(n, n2, n3, n4);
        this.hb = hb;
        this.offset = offset;
    }
    
    LongBuffer(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, null, 0);
    }
    
    public static LongBuffer allocate(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return new HeapLongBuffer(n, n);
    }
    
    public static LongBuffer wrap(final long[] array, final int n, final int n2) {
        try {
            return new HeapLongBuffer(array, n, n2);
        }
        catch (IllegalArgumentException ex) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public static LongBuffer wrap(final long[] array) {
        return wrap(array, 0, array.length);
    }
    
    public abstract LongBuffer slice();
    
    public abstract LongBuffer duplicate();
    
    public abstract LongBuffer asReadOnlyBuffer();
    
    public abstract long get();
    
    public abstract LongBuffer put(final long p0);
    
    public abstract long get(final int p0);
    
    public abstract LongBuffer put(final int p0, final long p1);
    
    public LongBuffer get(final long[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferUnderflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            array[i] = this.get();
        }
        return this;
    }
    
    public LongBuffer get(final long[] array) {
        return this.get(array, 0, array.length);
    }
    
    public LongBuffer put(final LongBuffer longBuffer) {
        if (longBuffer == this) {
            throw new IllegalArgumentException();
        }
        if (this.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        final int remaining = longBuffer.remaining();
        if (remaining > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int i = 0; i < remaining; ++i) {
            this.put(longBuffer.get());
        }
        return this;
    }
    
    public LongBuffer put(final long[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            this.put(array[i]);
        }
        return this;
    }
    
    public final LongBuffer put(final long[] array) {
        return this.put(array, 0, array.length);
    }
    
    @Override
    public final boolean hasArray() {
        return this.hb != null && !this.isReadOnly;
    }
    
    @Override
    public final long[] array() {
        if (this.hb == null) {
            throw new UnsupportedOperationException();
        }
        if (this.isReadOnly) {
            throw new ReadOnlyBufferException();
        }
        return this.hb;
    }
    
    @Override
    public final int arrayOffset() {
        if (this.hb == null) {
            throw new UnsupportedOperationException();
        }
        if (this.isReadOnly) {
            throw new ReadOnlyBufferException();
        }
        return this.offset;
    }
    
    public abstract LongBuffer compact();
    
    @Override
    public abstract boolean isDirect();
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append("[pos=");
        sb.append(this.position());
        sb.append(" lim=");
        sb.append(this.limit());
        sb.append(" cap=");
        sb.append(this.capacity());
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        int n = 1;
        for (int position = this.position(), i = this.limit() - 1; i >= position; --i) {
            n = 31 * n + (int)this.get(i);
        }
        return n;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongBuffer)) {
            return false;
        }
        final LongBuffer longBuffer = (LongBuffer)o;
        if (this.remaining() != longBuffer.remaining()) {
            return false;
        }
        for (int position = this.position(), i = this.limit() - 1, n = longBuffer.limit() - 1; i >= position; --i, --n) {
            if (!equals(this.get(i), longBuffer.get(n))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean equals(final long n, final long n2) {
        return n == n2;
    }
    
    @Override
    public int compareTo(final LongBuffer longBuffer) {
        for (int n = this.position() + Math.min(this.remaining(), longBuffer.remaining()), i = this.position(), position = longBuffer.position(); i < n; ++i, ++position) {
            final int compare = compare(this.get(i), longBuffer.get(position));
            if (compare != 0) {
                return compare;
            }
        }
        return this.remaining() - longBuffer.remaining();
    }
    
    private static int compare(final long n, final long n2) {
        return Long.compare(n, n2);
    }
    
    public abstract ByteOrder order();
}
