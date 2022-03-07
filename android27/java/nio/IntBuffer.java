package java.nio;

public abstract class IntBuffer extends Buffer implements Comparable<IntBuffer>
{
    final int[] hb;
    final int offset;
    boolean isReadOnly;
    
    IntBuffer(final int n, final int n2, final int n3, final int n4, final int[] hb, final int offset) {
        super(n, n2, n3, n4);
        this.hb = hb;
        this.offset = offset;
    }
    
    IntBuffer(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, null, 0);
    }
    
    public static IntBuffer allocate(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return new HeapIntBuffer(n, n);
    }
    
    public static IntBuffer wrap(final int[] array, final int n, final int n2) {
        try {
            return new HeapIntBuffer(array, n, n2);
        }
        catch (IllegalArgumentException ex) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public static IntBuffer wrap(final int[] array) {
        return wrap(array, 0, array.length);
    }
    
    public abstract IntBuffer slice();
    
    public abstract IntBuffer duplicate();
    
    public abstract IntBuffer asReadOnlyBuffer();
    
    public abstract int get();
    
    public abstract IntBuffer put(final int p0);
    
    public abstract int get(final int p0);
    
    public abstract IntBuffer put(final int p0, final int p1);
    
    public IntBuffer get(final int[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferUnderflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            array[i] = this.get();
        }
        return this;
    }
    
    public IntBuffer get(final int[] array) {
        return this.get(array, 0, array.length);
    }
    
    public IntBuffer put(final IntBuffer intBuffer) {
        if (intBuffer == this) {
            throw new IllegalArgumentException();
        }
        if (this.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        final int remaining = intBuffer.remaining();
        if (remaining > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int i = 0; i < remaining; ++i) {
            this.put(intBuffer.get());
        }
        return this;
    }
    
    public IntBuffer put(final int[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            this.put(array[i]);
        }
        return this;
    }
    
    public final IntBuffer put(final int[] array) {
        return this.put(array, 0, array.length);
    }
    
    @Override
    public final boolean hasArray() {
        return this.hb != null && !this.isReadOnly;
    }
    
    @Override
    public final int[] array() {
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
    
    public abstract IntBuffer compact();
    
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
            n = 31 * n + this.get(i);
        }
        return n;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntBuffer)) {
            return false;
        }
        final IntBuffer intBuffer = (IntBuffer)o;
        if (this.remaining() != intBuffer.remaining()) {
            return false;
        }
        for (int position = this.position(), i = this.limit() - 1, n = intBuffer.limit() - 1; i >= position; --i, --n) {
            if (!equals(this.get(i), intBuffer.get(n))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean equals(final int n, final int n2) {
        return n == n2;
    }
    
    @Override
    public int compareTo(final IntBuffer intBuffer) {
        for (int n = this.position() + Math.min(this.remaining(), intBuffer.remaining()), i = this.position(), position = intBuffer.position(); i < n; ++i, ++position) {
            final int compare = compare(this.get(i), intBuffer.get(position));
            if (compare != 0) {
                return compare;
            }
        }
        return this.remaining() - intBuffer.remaining();
    }
    
    private static int compare(final int n, final int n2) {
        return Integer.compare(n, n2);
    }
    
    public abstract ByteOrder order();
}
