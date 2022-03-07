package java.nio;

import java.io.*;
import java.util.stream.*;
import java.util.*;

public abstract class CharBuffer extends Buffer implements Comparable<CharBuffer>, Appendable, CharSequence, Readable
{
    final char[] hb;
    final int offset;
    boolean isReadOnly;
    
    CharBuffer(final int n, final int n2, final int n3, final int n4, final char[] hb, final int offset) {
        super(n, n2, n3, n4);
        this.hb = hb;
        this.offset = offset;
    }
    
    CharBuffer(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, null, 0);
    }
    
    public static CharBuffer allocate(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return new HeapCharBuffer(n, n);
    }
    
    public static CharBuffer wrap(final char[] array, final int n, final int n2) {
        try {
            return new HeapCharBuffer(array, n, n2);
        }
        catch (IllegalArgumentException ex) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public static CharBuffer wrap(final char[] array) {
        return wrap(array, 0, array.length);
    }
    
    @Override
    public int read(final CharBuffer charBuffer) throws IOException {
        final int remaining = charBuffer.remaining();
        final int remaining2 = this.remaining();
        if (remaining2 == 0) {
            return -1;
        }
        final int min = Math.min(remaining2, remaining);
        final int limit = this.limit();
        if (remaining < remaining2) {
            this.limit(this.position() + min);
        }
        try {
            if (min > 0) {
                charBuffer.put(this);
            }
        }
        finally {
            this.limit(limit);
        }
        return min;
    }
    
    public static CharBuffer wrap(final CharSequence charSequence, final int n, final int n2) {
        try {
            return new StringCharBuffer(charSequence, n, n2);
        }
        catch (IllegalArgumentException ex) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public static CharBuffer wrap(final CharSequence charSequence) {
        return wrap(charSequence, 0, charSequence.length());
    }
    
    public abstract CharBuffer slice();
    
    public abstract CharBuffer duplicate();
    
    public abstract CharBuffer asReadOnlyBuffer();
    
    public abstract char get();
    
    public abstract CharBuffer put(final char p0);
    
    public abstract char get(final int p0);
    
    abstract char getUnchecked(final int p0);
    
    public abstract CharBuffer put(final int p0, final char p1);
    
    public CharBuffer get(final char[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferUnderflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            array[i] = this.get();
        }
        return this;
    }
    
    public CharBuffer get(final char[] array) {
        return this.get(array, 0, array.length);
    }
    
    public CharBuffer put(final CharBuffer charBuffer) {
        if (charBuffer == this) {
            throw new IllegalArgumentException();
        }
        if (this.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        final int remaining = charBuffer.remaining();
        if (remaining > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int i = 0; i < remaining; ++i) {
            this.put(charBuffer.get());
        }
        return this;
    }
    
    public CharBuffer put(final char[] array, final int n, final int n2) {
        Buffer.checkBounds(n, n2, array.length);
        if (n2 > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            this.put(array[i]);
        }
        return this;
    }
    
    public final CharBuffer put(final char[] array) {
        return this.put(array, 0, array.length);
    }
    
    public CharBuffer put(final String s, final int n, final int n2) {
        Buffer.checkBounds(n, n2 - n, s.length());
        if (this.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (n2 - n > this.remaining()) {
            throw new BufferOverflowException();
        }
        for (int i = n; i < n2; ++i) {
            this.put(s.charAt(i));
        }
        return this;
    }
    
    public final CharBuffer put(final String s) {
        return this.put(s, 0, s.length());
    }
    
    @Override
    public final boolean hasArray() {
        return this.hb != null && !this.isReadOnly;
    }
    
    @Override
    public final char[] array() {
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
    
    public abstract CharBuffer compact();
    
    @Override
    public abstract boolean isDirect();
    
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
        if (!(o instanceof CharBuffer)) {
            return false;
        }
        final CharBuffer charBuffer = (CharBuffer)o;
        if (this.remaining() != charBuffer.remaining()) {
            return false;
        }
        for (int position = this.position(), i = this.limit() - 1, n = charBuffer.limit() - 1; i >= position; --i, --n) {
            if (!equals(this.get(i), charBuffer.get(n))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean equals(final char c, final char c2) {
        return c == c2;
    }
    
    @Override
    public int compareTo(final CharBuffer charBuffer) {
        for (int n = this.position() + Math.min(this.remaining(), charBuffer.remaining()), i = this.position(), position = charBuffer.position(); i < n; ++i, ++position) {
            final int compare = compare(this.get(i), charBuffer.get(position));
            if (compare != 0) {
                return compare;
            }
        }
        return this.remaining() - charBuffer.remaining();
    }
    
    private static int compare(final char c, final char c2) {
        return Character.compare(c, c2);
    }
    
    @Override
    public String toString() {
        return this.toString(this.position(), this.limit());
    }
    
    abstract String toString(final int p0, final int p1);
    
    @Override
    public final int length() {
        return this.remaining();
    }
    
    @Override
    public final char charAt(final int n) {
        return this.get(this.position() + this.checkIndex(n, 1));
    }
    
    @Override
    public abstract CharBuffer subSequence(final int p0, final int p1);
    
    @Override
    public CharBuffer append(final CharSequence charSequence) {
        if (charSequence == null) {
            return this.put("null");
        }
        return this.put(charSequence.toString());
    }
    
    @Override
    public CharBuffer append(final CharSequence charSequence, final int n, final int n2) {
        return this.put(((charSequence == null) ? "null" : charSequence).subSequence(n, n2).toString());
    }
    
    @Override
    public CharBuffer append(final char c) {
        return this.put(c);
    }
    
    public abstract ByteOrder order();
    
    @Override
    public IntStream chars() {
        return StreamSupport.intStream(() -> new CharBufferSpliterator(this), 16464, false);
    }
}
