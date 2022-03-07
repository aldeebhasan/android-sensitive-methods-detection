package java.lang;

import java.io.*;

public final class StringBuilder extends AbstractStringBuilder implements Serializable, CharSequence
{
    static final long serialVersionUID = 4383685877147921099L;
    
    public StringBuilder() {
        super(16);
    }
    
    public StringBuilder(final int n) {
        super(n);
    }
    
    public StringBuilder(final String s) {
        super(s.length() + 16);
        this.append(s);
    }
    
    public StringBuilder(final CharSequence charSequence) {
        this(charSequence.length() + 16);
        this.append(charSequence);
    }
    
    @Override
    public StringBuilder append(final Object o) {
        return this.append(String.valueOf(o));
    }
    
    @Override
    public StringBuilder append(final String s) {
        super.append(s);
        return this;
    }
    
    @Override
    public StringBuilder append(final StringBuffer sb) {
        super.append(sb);
        return this;
    }
    
    @Override
    public StringBuilder append(final CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }
    
    @Override
    public StringBuilder append(final CharSequence charSequence, final int n, final int n2) {
        super.append(charSequence, n, n2);
        return this;
    }
    
    @Override
    public StringBuilder append(final char[] array) {
        super.append(array);
        return this;
    }
    
    @Override
    public StringBuilder append(final char[] array, final int n, final int n2) {
        super.append(array, n, n2);
        return this;
    }
    
    @Override
    public StringBuilder append(final boolean b) {
        super.append(b);
        return this;
    }
    
    @Override
    public StringBuilder append(final char c) {
        super.append(c);
        return this;
    }
    
    @Override
    public StringBuilder append(final int n) {
        super.append(n);
        return this;
    }
    
    @Override
    public StringBuilder append(final long n) {
        super.append(n);
        return this;
    }
    
    @Override
    public StringBuilder append(final float n) {
        super.append(n);
        return this;
    }
    
    @Override
    public StringBuilder append(final double n) {
        super.append(n);
        return this;
    }
    
    @Override
    public StringBuilder appendCodePoint(final int n) {
        super.appendCodePoint(n);
        return this;
    }
    
    @Override
    public StringBuilder delete(final int n, final int n2) {
        super.delete(n, n2);
        return this;
    }
    
    @Override
    public StringBuilder deleteCharAt(final int n) {
        super.deleteCharAt(n);
        return this;
    }
    
    @Override
    public StringBuilder replace(final int n, final int n2, final String s) {
        super.replace(n, n2, s);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final char[] array, final int n2, final int n3) {
        super.insert(n, array, n2, n3);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final Object o) {
        super.insert(n, o);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final String s) {
        super.insert(n, s);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final char[] array) {
        super.insert(n, array);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final CharSequence charSequence) {
        super.insert(n, charSequence);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final CharSequence charSequence, final int n2, final int n3) {
        super.insert(n, charSequence, n2, n3);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final boolean b) {
        super.insert(n, b);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final char c) {
        super.insert(n, c);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final int n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final long n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final float n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public StringBuilder insert(final int n, final double n2) {
        super.insert(n, n2);
        return this;
    }
    
    @Override
    public int indexOf(final String s) {
        return super.indexOf(s);
    }
    
    @Override
    public int indexOf(final String s, final int n) {
        return super.indexOf(s, n);
    }
    
    @Override
    public int lastIndexOf(final String s) {
        return super.lastIndexOf(s);
    }
    
    @Override
    public int lastIndexOf(final String s, final int n) {
        return super.lastIndexOf(s, n);
    }
    
    @Override
    public StringBuilder reverse() {
        super.reverse();
        return this;
    }
    
    @Override
    public String toString() {
        return new String(this.value, 0, this.count);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.count);
        objectOutputStream.writeObject(this.value);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count = objectInputStream.readInt();
        this.value = (char[])objectInputStream.readObject();
    }
}
