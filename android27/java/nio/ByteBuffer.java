package java.nio;

public abstract class ByteBuffer extends Buffer implements Comparable<ByteBuffer>
{
    final byte[] hb;
    final int offset;
    boolean isReadOnly;
    boolean bigEndian;
    boolean nativeByteOrder;
    
    ByteBuffer(final int n, final int n2, final int n3, final int n4, final byte[] hb, final int offset) {
        super(n, n2, n3, n4);
        this.bigEndian = true;
        this.nativeByteOrder = (Bits.byteOrder() == ByteOrder.BIG_ENDIAN);
        this.hb = hb;
        this.offset = offset;
    }
    
    ByteBuffer(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, null, 0);
    }
    
    public static ByteBuffer allocateDirect(final int n) {
        return new DirectByteBuffer(n);
    }
    
    public static ByteBuffer allocate(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return new HeapByteBuffer(n, n);
    }
    
    public static ByteBuffer wrap(final byte[] array, final int n, final int n2) {
        try {
            return new HeapByteBuffer(array, n, n2);
        }
        catch (IllegalArgumentException ex) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public static ByteBuffer wrap(final byte[] array) {
        return wrap(array, 0, array.length);
    }
    
    public abstract ByteBuffer slice();
    
    public abstract ByteBuffer duplicate();
    
    public abstract ByteBuffer asReadOnlyBuffer();
    
    public abstract byte get();
    
    public abstract ByteBuffer put(final byte p0);
    
    public abstract byte get(final int p0);
    
    public abstract ByteBuffer put(final int p0, final byte p1);
    
    public ByteBuffer get(final byte[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferUnderflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            array[i] = this.get();
        }
        return this;
    }
    
    public ByteBuffer get(final byte[] array) {
        return this.get(array, 0, array.length);
    }
    
    public ByteBuffer put(final ByteBuffer byteBuffer) {
        if (byteBuffer == this) {
            throw new IllegalArgumentException();
        }
        if (this.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        final int remaining = byteBuffer.remaining();
        if (remaining > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int i = 0; i < remaining; ++i) {
            this.put(byteBuffer.get());
        }
        return this;
    }
    
    public ByteBuffer put(final byte[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            this.put(array[i]);
        }
        return this;
    }
    
    public final ByteBuffer put(final byte[] array) {
        return this.put(array, 0, array.length);
    }
    
    @Override
    public final boolean hasArray() {
        return this.hb != null && !this.isReadOnly;
    }
    
    @Override
    public final byte[] array() {
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
    
    public abstract ByteBuffer compact();
    
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
        if (!(o instanceof ByteBuffer)) {
            return false;
        }
        final ByteBuffer byteBuffer = (ByteBuffer)o;
        if (this.remaining() != byteBuffer.remaining()) {
            return false;
        }
        for (int position = this.position(), i = this.limit() - 1, n = byteBuffer.limit() - 1; i >= position; --i, --n) {
            if (!equals(this.get(i), byteBuffer.get(n))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean equals(final byte b, final byte b2) {
        return b == b2;
    }
    
    @Override
    public int compareTo(final ByteBuffer byteBuffer) {
        for (int n = this.position() + Math.min(this.remaining(), byteBuffer.remaining()), i = this.position(), position = byteBuffer.position(); i < n; ++i, ++position) {
            final int compare = compare(this.get(i), byteBuffer.get(position));
            if (compare != 0) {
                return compare;
            }
        }
        return this.remaining() - byteBuffer.remaining();
    }
    
    private static int compare(final byte b, final byte b2) {
        return Byte.compare(b, b2);
    }
    
    public final ByteOrder order() {
        return this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
    }
    
    public final ByteBuffer order(final ByteOrder byteOrder) {
        this.bigEndian = (byteOrder == ByteOrder.BIG_ENDIAN);
        this.nativeByteOrder = (this.bigEndian == (Bits.byteOrder() == ByteOrder.BIG_ENDIAN));
        return this;
    }
    
    abstract byte _get(final int p0);
    
    abstract void _put(final int p0, final byte p1);
    
    public abstract char getChar();
    
    public abstract ByteBuffer putChar(final char p0);
    
    public abstract char getChar(final int p0);
    
    public abstract ByteBuffer putChar(final int p0, final char p1);
    
    public abstract CharBuffer asCharBuffer();
    
    public abstract short getShort();
    
    public abstract ByteBuffer putShort(final short p0);
    
    public abstract short getShort(final int p0);
    
    public abstract ByteBuffer putShort(final int p0, final short p1);
    
    public abstract ShortBuffer asShortBuffer();
    
    public abstract int getInt();
    
    public abstract ByteBuffer putInt(final int p0);
    
    public abstract int getInt(final int p0);
    
    public abstract ByteBuffer putInt(final int p0, final int p1);
    
    public abstract IntBuffer asIntBuffer();
    
    public abstract long getLong();
    
    public abstract ByteBuffer putLong(final long p0);
    
    public abstract long getLong(final int p0);
    
    public abstract ByteBuffer putLong(final int p0, final long p1);
    
    public abstract LongBuffer asLongBuffer();
    
    public abstract float getFloat();
    
    public abstract ByteBuffer putFloat(final float p0);
    
    public abstract float getFloat(final int p0);
    
    public abstract ByteBuffer putFloat(final int p0, final float p1);
    
    public abstract FloatBuffer asFloatBuffer();
    
    public abstract double getDouble();
    
    public abstract ByteBuffer putDouble(final double p0);
    
    public abstract double getDouble(final int p0);
    
    public abstract ByteBuffer putDouble(final int p0, final double p1);
    
    public abstract DoubleBuffer asDoubleBuffer();
}
