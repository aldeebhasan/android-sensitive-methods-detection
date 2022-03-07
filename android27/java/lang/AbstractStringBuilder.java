package java.lang;

import java.util.*;
import sun.misc.*;
import java.io.*;

abstract class AbstractStringBuilder implements Appendable, CharSequence
{
    char[] value;
    int count;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    
    AbstractStringBuilder() {
    }
    
    AbstractStringBuilder(final int n) {
        this.value = new char[n];
    }
    
    @Override
    public int length() {
        return this.count;
    }
    
    public int capacity() {
        return this.value.length;
    }
    
    public void ensureCapacity(final int n) {
        if (n > 0) {
            this.ensureCapacityInternal(n);
        }
    }
    
    private void ensureCapacityInternal(final int n) {
        if (n - this.value.length > 0) {
            this.value = Arrays.copyOf(this.value, this.newCapacity(n));
        }
    }
    
    private int newCapacity(final int n) {
        int n2 = (this.value.length << 1) + 2;
        if (n2 - n < 0) {
            n2 = n;
        }
        return (n2 <= 0 || 2147483639 - n2 < 0) ? this.hugeCapacity(n) : n2;
    }
    
    private int hugeCapacity(final int n) {
        if (Integer.MAX_VALUE - n < 0) {
            throw new OutOfMemoryError();
        }
        return (n > 2147483639) ? n : 2147483639;
    }
    
    public void trimToSize() {
        if (this.count < this.value.length) {
            this.value = Arrays.copyOf(this.value, this.count);
        }
    }
    
    public void setLength(final int count) {
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }
        this.ensureCapacityInternal(count);
        if (this.count < count) {
            Arrays.fill(this.value, this.count, count, '\0');
        }
        this.count = count;
    }
    
    @Override
    public char charAt(final int n) {
        if (n < 0 || n >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return this.value[n];
    }
    
    public int codePointAt(final int n) {
        if (n < 0 || n >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return Character.codePointAtImpl(this.value, n, this.count);
    }
    
    public int codePointBefore(final int n) {
        final int n2 = n - 1;
        if (n2 < 0 || n2 >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return Character.codePointBeforeImpl(this.value, n, 0);
    }
    
    public int codePointCount(final int n, final int n2) {
        if (n < 0 || n2 > this.count || n > n2) {
            throw new IndexOutOfBoundsException();
        }
        return Character.codePointCountImpl(this.value, n, n2 - n);
    }
    
    public int offsetByCodePoints(final int n, final int n2) {
        if (n < 0 || n > this.count) {
            throw new IndexOutOfBoundsException();
        }
        return Character.offsetByCodePointsImpl(this.value, 0, this.count, n, n2);
    }
    
    public void getChars(final int n, final int n2, final char[] array, final int n3) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 < 0 || n2 > this.count) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n > n2) {
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        }
        System.arraycopy(this.value, n, array, n3, n2 - n);
    }
    
    public void setCharAt(final int n, final char c) {
        if (n < 0 || n >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        this.value[n] = c;
    }
    
    public AbstractStringBuilder append(final Object o) {
        return this.append(String.valueOf(o));
    }
    
    public AbstractStringBuilder append(final String s) {
        if (s == null) {
            return this.appendNull();
        }
        final int length = s.length();
        this.ensureCapacityInternal(this.count + length);
        s.getChars(0, length, this.value, this.count);
        this.count += length;
        return this;
    }
    
    public AbstractStringBuilder append(final StringBuffer sb) {
        if (sb == null) {
            return this.appendNull();
        }
        final int length = sb.length();
        this.ensureCapacityInternal(this.count + length);
        sb.getChars(0, length, this.value, this.count);
        this.count += length;
        return this;
    }
    
    AbstractStringBuilder append(final AbstractStringBuilder abstractStringBuilder) {
        if (abstractStringBuilder == null) {
            return this.appendNull();
        }
        final int length = abstractStringBuilder.length();
        this.ensureCapacityInternal(this.count + length);
        abstractStringBuilder.getChars(0, length, this.value, this.count);
        this.count += length;
        return this;
    }
    
    @Override
    public AbstractStringBuilder append(final CharSequence charSequence) {
        if (charSequence == null) {
            return this.appendNull();
        }
        if (charSequence instanceof String) {
            return this.append((String)charSequence);
        }
        if (charSequence instanceof AbstractStringBuilder) {
            return this.append((AbstractStringBuilder)charSequence);
        }
        return this.append(charSequence, 0, charSequence.length());
    }
    
    private AbstractStringBuilder appendNull() {
        int count = this.count;
        this.ensureCapacityInternal(count + 4);
        final char[] value = this.value;
        value[count++] = 'n';
        value[count++] = 'u';
        value[count++] = 'l';
        value[count++] = 'l';
        this.count = count;
        return this;
    }
    
    @Override
    public AbstractStringBuilder append(CharSequence charSequence, final int n, final int n2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        if (n < 0 || n > n2 || n2 > charSequence.length()) {
            throw new IndexOutOfBoundsException("start " + n + ", end " + n2 + ", s.length() " + charSequence.length());
        }
        final int n3 = n2 - n;
        this.ensureCapacityInternal(this.count + n3);
        for (int i = n, count = this.count; i < n2; ++i, ++count) {
            this.value[count] = charSequence.charAt(i);
        }
        this.count += n3;
        return this;
    }
    
    public AbstractStringBuilder append(final char[] array) {
        final int length = array.length;
        this.ensureCapacityInternal(this.count + length);
        System.arraycopy(array, 0, this.value, this.count, length);
        this.count += length;
        return this;
    }
    
    public AbstractStringBuilder append(final char[] array, final int n, final int n2) {
        if (n2 > 0) {
            this.ensureCapacityInternal(this.count + n2);
        }
        System.arraycopy(array, n, this.value, this.count, n2);
        this.count += n2;
        return this;
    }
    
    public AbstractStringBuilder append(final boolean b) {
        if (b) {
            this.ensureCapacityInternal(this.count + 4);
            this.value[this.count++] = 't';
            this.value[this.count++] = 'r';
            this.value[this.count++] = 'u';
            this.value[this.count++] = 'e';
        }
        else {
            this.ensureCapacityInternal(this.count + 5);
            this.value[this.count++] = 'f';
            this.value[this.count++] = 'a';
            this.value[this.count++] = 'l';
            this.value[this.count++] = 's';
            this.value[this.count++] = 'e';
        }
        return this;
    }
    
    @Override
    public AbstractStringBuilder append(final char c) {
        this.ensureCapacityInternal(this.count + 1);
        this.value[this.count++] = c;
        return this;
    }
    
    public AbstractStringBuilder append(final int n) {
        if (n == Integer.MIN_VALUE) {
            this.append("-2147483648");
            return this;
        }
        final int count = this.count + ((n < 0) ? (Integer.stringSize(-n) + 1) : Integer.stringSize(n));
        this.ensureCapacityInternal(count);
        Integer.getChars(n, count, this.value);
        this.count = count;
        return this;
    }
    
    public AbstractStringBuilder append(final long n) {
        if (n == Long.MIN_VALUE) {
            this.append("-9223372036854775808");
            return this;
        }
        final int count = this.count + ((n < 0L) ? (Long.stringSize(-n) + 1) : Long.stringSize(n));
        this.ensureCapacityInternal(count);
        Long.getChars(n, count, this.value);
        this.count = count;
        return this;
    }
    
    public AbstractStringBuilder append(final float n) {
        FloatingDecimal.appendTo(n, this);
        return this;
    }
    
    public AbstractStringBuilder append(final double n) {
        FloatingDecimal.appendTo(n, this);
        return this;
    }
    
    public AbstractStringBuilder delete(final int n, int count) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (count > this.count) {
            count = this.count;
        }
        if (n > count) {
            throw new StringIndexOutOfBoundsException();
        }
        final int n2 = count - n;
        if (n2 > 0) {
            System.arraycopy(this.value, n + n2, this.value, n, this.count - count);
            this.count -= n2;
        }
        return this;
    }
    
    public AbstractStringBuilder appendCodePoint(final int n) {
        final int count = this.count;
        if (Character.isBmpCodePoint(n)) {
            this.ensureCapacityInternal(count + 1);
            this.value[count] = (char)n;
            this.count = count + 1;
        }
        else {
            if (!Character.isValidCodePoint(n)) {
                throw new IllegalArgumentException();
            }
            this.ensureCapacityInternal(count + 2);
            Character.toSurrogates(n, this.value, count);
            this.count = count + 2;
        }
        return this;
    }
    
    public AbstractStringBuilder deleteCharAt(final int n) {
        if (n < 0 || n >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        System.arraycopy(this.value, n + 1, this.value, n, this.count - n - 1);
        --this.count;
        return this;
    }
    
    public AbstractStringBuilder replace(final int n, int count, final String s) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n > this.count) {
            throw new StringIndexOutOfBoundsException("start > length()");
        }
        if (n > count) {
            throw new StringIndexOutOfBoundsException("start > end");
        }
        if (count > this.count) {
            count = this.count;
        }
        final int length = s.length();
        final int count2 = this.count + length - (count - n);
        this.ensureCapacityInternal(count2);
        System.arraycopy(this.value, count, this.value, n + length, this.count - count);
        s.getChars(this.value, n);
        this.count = count2;
        return this;
    }
    
    public String substring(final int n) {
        return this.substring(n, this.count);
    }
    
    @Override
    public CharSequence subSequence(final int n, final int n2) {
        return this.substring(n, n2);
    }
    
    public String substring(final int n, final int n2) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > this.count) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n > n2) {
            throw new StringIndexOutOfBoundsException(n2 - n);
        }
        return new String(this.value, n, n2 - n);
    }
    
    public AbstractStringBuilder insert(final int n, final char[] array, final int n2, final int n3) {
        if (n < 0 || n > this.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 < 0 || n3 < 0 || n2 > array.length - n3) {
            throw new StringIndexOutOfBoundsException("offset " + n2 + ", len " + n3 + ", str.length " + array.length);
        }
        this.ensureCapacityInternal(this.count + n3);
        System.arraycopy(this.value, n, this.value, n + n3, this.count - n);
        System.arraycopy(array, n2, this.value, n, n3);
        this.count += n3;
        return this;
    }
    
    public AbstractStringBuilder insert(final int n, final Object o) {
        return this.insert(n, String.valueOf(o));
    }
    
    public AbstractStringBuilder insert(final int n, String s) {
        if (n < 0 || n > this.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (s == null) {
            s = "null";
        }
        final int length = s.length();
        this.ensureCapacityInternal(this.count + length);
        System.arraycopy(this.value, n, this.value, n + length, this.count - n);
        s.getChars(this.value, n);
        this.count += length;
        return this;
    }
    
    public AbstractStringBuilder insert(final int n, final char[] array) {
        if (n < 0 || n > this.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        final int length = array.length;
        this.ensureCapacityInternal(this.count + length);
        System.arraycopy(this.value, n, this.value, n + length, this.count - n);
        System.arraycopy(array, 0, this.value, n, length);
        this.count += length;
        return this;
    }
    
    public AbstractStringBuilder insert(final int n, CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "null";
        }
        if (charSequence instanceof String) {
            return this.insert(n, (String)charSequence);
        }
        return this.insert(n, charSequence, 0, charSequence.length());
    }
    
    public AbstractStringBuilder insert(int n, CharSequence charSequence, final int n2, final int n3) {
        if (charSequence == null) {
            charSequence = "null";
        }
        if (n < 0 || n > this.length()) {
            throw new IndexOutOfBoundsException("dstOffset " + n);
        }
        if (n2 < 0 || n3 < 0 || n2 > n3 || n3 > charSequence.length()) {
            throw new IndexOutOfBoundsException("start " + n2 + ", end " + n3 + ", s.length() " + charSequence.length());
        }
        final int n4 = n3 - n2;
        this.ensureCapacityInternal(this.count + n4);
        System.arraycopy(this.value, n, this.value, n + n4, this.count - n);
        for (int i = n2; i < n3; ++i) {
            this.value[n++] = charSequence.charAt(i);
        }
        this.count += n4;
        return this;
    }
    
    public AbstractStringBuilder insert(final int n, final boolean b) {
        return this.insert(n, String.valueOf(b));
    }
    
    public AbstractStringBuilder insert(final int n, final char c) {
        this.ensureCapacityInternal(this.count + 1);
        System.arraycopy(this.value, n, this.value, n + 1, this.count - n);
        this.value[n] = c;
        ++this.count;
        return this;
    }
    
    public AbstractStringBuilder insert(final int n, final int n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public AbstractStringBuilder insert(final int n, final long n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public AbstractStringBuilder insert(final int n, final float n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public AbstractStringBuilder insert(final int n, final double n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public int indexOf(final String s) {
        return this.indexOf(s, 0);
    }
    
    public int indexOf(final String s, final int n) {
        return String.indexOf(this.value, 0, this.count, s, n);
    }
    
    public int lastIndexOf(final String s) {
        return this.lastIndexOf(s, this.count);
    }
    
    public int lastIndexOf(final String s, final int n) {
        return String.lastIndexOf(this.value, 0, this.count, s, n);
    }
    
    public AbstractStringBuilder reverse() {
        boolean b = false;
        final int n = this.count - 1;
        for (int i = n - 1 >> 1; i >= 0; --i) {
            final int n2 = n - i;
            final char c = this.value[i];
            final char c2 = this.value[n2];
            this.value[i] = c2;
            this.value[n2] = c;
            if (Character.isSurrogate(c) || Character.isSurrogate(c2)) {
                b = true;
            }
        }
        if (b) {
            this.reverseAllValidSurrogatePairs();
        }
        return this;
    }
    
    private void reverseAllValidSurrogatePairs() {
        for (int i = 0; i < this.count - 1; ++i) {
            final char c = this.value[i];
            if (Character.isLowSurrogate(c)) {
                final char c2 = this.value[i + 1];
                if (Character.isHighSurrogate(c2)) {
                    this.value[i++] = c2;
                    this.value[i] = c;
                }
            }
        }
    }
    
    @Override
    public abstract String toString();
    
    final char[] getValue() {
        return this.value;
    }
}
