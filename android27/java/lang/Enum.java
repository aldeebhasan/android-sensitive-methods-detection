package java.lang;

import java.io.*;

public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Serializable
{
    private final String name;
    private final int ordinal;
    
    public final String name() {
        return this.name;
    }
    
    public final int ordinal() {
        return this.ordinal;
    }
    
    protected Enum(final String name, final int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    @Override
    public final int compareTo(final E e) {
        if (this.getClass() != e.getClass() && this.getDeclaringClass() != e.getDeclaringClass()) {
            throw new ClassCastException();
        }
        return this.ordinal - e.ordinal;
    }
    
    public final Class<E> getDeclaringClass() {
        final Class<? extends Enum> class1 = this.getClass();
        final Class<? super Enum> superclass = class1.getSuperclass();
        return (Class<E>)((superclass == Enum.class) ? class1 : superclass);
    }
    
    public static <T extends Enum<T>> T valueOf(final Class<T> clazz, final String s) {
        final Enum<T> enum1 = clazz.enumConstantDirectory().get(s);
        if (enum1 != null) {
            return (T)enum1;
        }
        if (s == null) {
            throw new NullPointerException("Name is null");
        }
        throw new IllegalArgumentException("No enum constant " + clazz.getCanonicalName() + "." + s);
    }
    
    @Override
    protected final void finalize() {
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("can't deserialize enum");
    }
    
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("can't deserialize enum");
    }
}
