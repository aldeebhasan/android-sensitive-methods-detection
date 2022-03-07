package java.nio;

public abstract class FloatBuffer extends Buffer implements Comparable<FloatBuffer>
{
    final float[] hb;
    final int offset;
    boolean isReadOnly;
    
    FloatBuffer(final int n, final int n2, final int n3, final int n4, final float[] hb, final int offset) {
        super(n, n2, n3, n4);
        this.hb = hb;
        this.offset = offset;
    }
    
    FloatBuffer(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, null, 0);
    }
    
    public static FloatBuffer allocate(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return new HeapFloatBuffer(n, n);
    }
    
    public static FloatBuffer wrap(final float[] array, final int n, final int n2) {
        try {
            return new HeapFloatBuffer(array, n, n2);
        }
        catch (IllegalArgumentException ex) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public static FloatBuffer wrap(final float[] array) {
        return wrap(array, 0, array.length);
    }
    
    public abstract FloatBuffer slice();
    
    public abstract FloatBuffer duplicate();
    
    public abstract FloatBuffer asReadOnlyBuffer();
    
    public abstract float get();
    
    public abstract FloatBuffer put(final float p0);
    
    public abstract float get(final int p0);
    
    public abstract FloatBuffer put(final int p0, final float p1);
    
    public FloatBuffer get(final float[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferUnderflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            array[i] = this.get();
        }
        return this;
    }
    
    public FloatBuffer get(final float[] array) {
        return this.get(array, 0, array.length);
    }
    
    public FloatBuffer put(final FloatBuffer floatBuffer) {
        if (floatBuffer == this) {
            throw new IllegalArgumentException();
        }
        if (this.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        final int remaining = floatBuffer.remaining();
        if (remaining > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int i = 0; i < remaining; ++i) {
            this.put(floatBuffer.get());
        }
        return this;
    }
    
    public FloatBuffer put(final float[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            this.put(array[i]);
        }
        return this;
    }
    
    public final FloatBuffer put(final float[] array) {
        return this.put(array, 0, array.length);
    }
    
    @Override
    public final boolean hasArray() {
        return this.hb != null && !this.isReadOnly;
    }
    
    @Override
    public final float[] array() {
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
    
    public abstract FloatBuffer compact();
    
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
        if (!(o instanceof FloatBuffer)) {
            return false;
        }
        final FloatBuffer floatBuffer = (FloatBuffer)o;
        if (this.remaining() != floatBuffer.remaining()) {
            return false;
        }
        for (int position = this.position(), i = this.limit() - 1, n = floatBuffer.limit() - 1; i >= position; --i, --n) {
            if (!equals(this.get(i), floatBuffer.get(n))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean equals(final float n, final float n2) {
        return n == n2 || (Float.isNaN(n) && Float.isNaN(n2));
    }
    
    @Override
    public int compareTo(final FloatBuffer floatBuffer) {
        for (int n = this.position() + Math.min(this.remaining(), floatBuffer.remaining()), i = this.position(), position = floatBuffer.position(); i < n; ++i, ++position) {
            final int compare = compare(this.get(i), floatBuffer.get(position));
            if (compare != 0) {
                return compare;
            }
        }
        return this.remaining() - floatBuffer.remaining();
    }
    
    private static int compare(final float n, final float n2) {
        return (n < n2) ? -1 : ((n > n2) ? 1 : ((n == n2) ? 0 : (Float.isNaN(n) ? (Float.isNaN(n2) ? 0 : 1) : -1)));
    }
    
    public abstract ByteOrder order();
}
