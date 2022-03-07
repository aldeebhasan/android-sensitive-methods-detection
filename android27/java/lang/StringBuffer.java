package java.lang;

import java.util.*;
import java.io.*;

public final class StringBuffer extends AbstractStringBuilder implements Serializable, CharSequence
{
    private transient char[] toStringCache;
    static final long serialVersionUID = 3388685877147921107L;
    private static final ObjectStreamField[] serialPersistentFields;
    
    public StringBuffer() {
        super(16);
    }
    
    public StringBuffer(final int n) {
        super(n);
    }
    
    public StringBuffer(final String s) {
        super(s.length() + 16);
        this.append(s);
    }
    
    public StringBuffer(final CharSequence charSequence) {
        this(charSequence.length() + 16);
        this.append(charSequence);
    }
    
    @Override
    public synchronized int length() {
        return this.count;
    }
    
    @Override
    public synchronized int capacity() {
        return this.value.length;
    }
    
    @Override
    public synchronized void ensureCapacity(final int n) {
        super.ensureCapacity(n);
    }
    
    @Override
    public synchronized void trimToSize() {
        super.trimToSize();
    }
    
    @Override
    public synchronized void setLength(final int length) {
        this.toStringCache = null;
        super.setLength(length);
    }
    
    @Override
    public synchronized char charAt(final int n) {
        if (n < 0 || n >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return this.value[n];
    }
    
    @Override
    public synchronized int codePointAt(final int n) {
        return super.codePointAt(n);
    }
    
    @Override
    public synchronized int codePointBefore(final int n) {
        return super.codePointBefore(n);
    }
    
    @Override
    public synchronized int codePointCount(final int n, final int n2) {
        return super.codePointCount(n, n2);
    }
    
    @Override
    public synchronized int offsetByCodePoints(final int n, final int n2) {
        return super.offsetByCodePoints(n, n2);
    }
    
    @Override
    public synchronized void getChars(final int n, final int n2, final char[] array, final int n3) {
        super.getChars(n, n2, array, n3);
    }
    
    @Override
    public synchronized void setCharAt(final int n, final char c) {
        if (n < 0 || n >= this.count) {
            throw new StringIndexOutOfBoundsException(n);
        }
        this.toStringCache = null;
        this.value[n] = c;
    }
    
    @Override
    public synchronized StringBuffer append(final Object o) {
        this.toStringCache = null;
        super.append(String.valueOf(o));
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final String s) {
        this.toStringCache = null;
        super.append(s);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final StringBuffer sb) {
        this.toStringCache = null;
        super.append(sb);
        return this;
    }
    
    @Override
    synchronized StringBuffer append(final AbstractStringBuilder abstractStringBuilder) {
        this.toStringCache = null;
        super.append(abstractStringBuilder);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final CharSequence charSequence) {
        this.toStringCache = null;
        super.append(charSequence);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final CharSequence charSequence, final int n, final int n2) {
        this.toStringCache = null;
        super.append(charSequence, n, n2);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final char[] array) {
        this.toStringCache = null;
        super.append(array);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final char[] array, final int n, final int n2) {
        this.toStringCache = null;
        super.append(array, n, n2);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final boolean b) {
        this.toStringCache = null;
        super.append(b);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final char c) {
        this.toStringCache = null;
        super.append(c);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final int n) {
        this.toStringCache = null;
        super.append(n);
        return this;
    }
    
    @Override
    public synchronized StringBuffer appendCodePoint(final int n) {
        this.toStringCache = null;
        super.appendCodePoint(n);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final long n) {
        this.toStringCache = null;
        super.append(n);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final float n) {
        this.toStringCache = null;
        super.append(n);
        return this;
    }
    
    @Override
    public synchronized StringBuffer append(final double n) {
        this.toStringCache = null;
        super.append(n);
        return this;
    }
    
    @Override
    public synchronized StringBuffer delete(final int n, final int n2) {
        this.toStringCache = null;
        super.delete(n, n2);
        return this;
    }
    
    @Override
    public synchronized StringBuffer deleteCharAt(final int n) {
        this.toStringCache = null;
        super.deleteCharAt(n);
        return this;
    }
    
    @Override
    public synchronized StringBuffer replace(final int n, final int n2, final String s) {
        this.toStringCache = null;
        super.replace(n, n2, s);
        return this;
    }
    
    @Override
    public synchronized String substring(final int n) {
        return this.substring(n, this.count);
    }
    
    @Override
    public synchronized CharSequence subSequence(final int n, final int n2) {
        return super.substring(n, n2);
    }
    
    @Override
    public synchronized String substring(final int n, final int n2) {
        return super.substring(n, n2);
    }
    
    @Override
    public synchronized StringBuffer insert(final int n, final char[] array, final int n2, final int n3) {
        this.toStringCache = null;
        super.insert(n, array, n2, n3);
        return this;
    }
    
    @Override
    public synchronized StringBuffer insert(final int n, final Object o) {
        this.toStringCache = null;
        super.insert(n, String.valueOf(o));
        return this;
    }
    
    @Override
    public synchronized StringBuffer insert(final int n, final String s) {
        this.toStringCache = null;
        super.insert(n, s);
        return this;
    }
    
    @Override
    public synchronized StringBuffer insert(final int n, final char[] array) {
        this.toStringCache = null;
        super.insert(n, array);
        return this;
    }
    
    @Override
    public StringBuffer insert(final int n, final CharSequence charSequence) {
        super.insert(n, charSequence);
        return this;
    }
    
    @Override
    public synchronized StringBuffer insert(final int n, final CharSequence charSequence, final int n2, final int n3) {
        this.toStringCache = null;
        super.insert(n, charSequence, n2, n3);
        return this;
    }
    
    @Override
    public StringBuffer insert(final int n, final boolean b) {
        super.insert(n, b);
        return this;
    }
    
    @Override
    public synchronized StringBuffer insert(final int n, final char c) {
        this.toStringCache = null;
        super.insert(n, c);
        return this;
    }
    
    @Override
    public StringBuffer insert(final int n, final int n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public StringBuffer insert(final int n, final long n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public StringBuffer insert(final int n, final float n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public StringBuffer insert(final int n, final double n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public int indexOf(final String s) {
        return super.indexOf(s);
    }
    
    @Override
    public synchronized int indexOf(final String s, final int n) {
        return super.indexOf(s, n);
    }
    
    @Override
    public int lastIndexOf(final String s) {
        return this.lastIndexOf(s, this.count);
    }
    
    @Override
    public synchronized int lastIndexOf(final String s, final int n) {
        return super.lastIndexOf(s, n);
    }
    
    @Override
    public synchronized StringBuffer reverse() {
        this.toStringCache = null;
        super.reverse();
        return this;
    }
    
    @Override
    public synchronized String toString() {
        if (this.toStringCache == null) {
            this.toStringCache = Arrays.copyOfRange(this.value, 0, this.count);
        }
        return new String(this.toStringCache, true);
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("value", this.value);
        putFields.put("count", this.count);
        putFields.put("shared", false);
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        this.value = (char[])fields.get("value", null);
        this.count = fields.get("count", 0);
    }
    
    static {
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("value", char[].class), new ObjectStreamField("count", Integer.TYPE), new ObjectStreamField("shared", Boolean.TYPE) };
    }
}
