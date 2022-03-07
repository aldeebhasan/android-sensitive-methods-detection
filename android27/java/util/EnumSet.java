package java.util;

import sun.misc.*;
import java.io.*;

public abstract class EnumSet<E extends Enum<E>> extends AbstractSet<E> implements Cloneable, Serializable
{
    final Class<E> elementType;
    final Enum<?>[] universe;
    private static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY;
    
    EnumSet(final Class<E> elementType, final Enum<?>[] universe) {
        this.elementType = elementType;
        this.universe = universe;
    }
    
    public static <E extends Enum<E>> EnumSet<E> noneOf(final Class<E> clazz) {
        final E[] universe = getUniverse(clazz);
        if (universe == null) {
            throw new ClassCastException(clazz + " not an enum");
        }
        if (universe.length <= 64) {
            return new RegularEnumSet<E>((Class<Enum>)clazz, universe);
        }
        return new JumboEnumSet<E>((Class<Enum>)clazz, universe);
    }
    
    public static <E extends Enum<E>> EnumSet<E> allOf(final Class<E> clazz) {
        final EnumSet<Enum> none = noneOf((Class<Enum>)clazz);
        none.addAll();
        return (EnumSet<E>)none;
    }
    
    abstract void addAll();
    
    public static <E extends Enum<E>> EnumSet<E> copyOf(final EnumSet<E> set) {
        return set.clone();
    }
    
    public static <E extends Enum<E>> EnumSet<E> copyOf(final Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return ((EnumSet<E>)collection).clone();
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Collection is empty");
        }
        final Iterator<E> iterator = (Iterator<E>)collection.iterator();
        final EnumSet<E> of = of(iterator.next());
        while (iterator.hasNext()) {
            of.add(iterator.next());
        }
        return of;
    }
    
    public static <E extends Enum<E>> EnumSet<E> complementOf(final EnumSet<E> set) {
        final EnumSet<Enum> copy = copyOf((EnumSet<Enum>)set);
        copy.complement();
        return (EnumSet<E>)copy;
    }
    
    public static <E extends Enum<E>> EnumSet<E> of(final E e) {
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.add(e);
        return none;
    }
    
    public static <E extends Enum<E>> EnumSet<E> of(final E e, final E e2) {
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.add(e);
        none.add(e2);
        return none;
    }
    
    public static <E extends Enum<E>> EnumSet<E> of(final E e, final E e2, final E e3) {
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.add(e);
        none.add(e2);
        none.add(e3);
        return none;
    }
    
    public static <E extends Enum<E>> EnumSet<E> of(final E e, final E e2, final E e3, final E e4) {
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.add(e);
        none.add(e2);
        none.add(e3);
        none.add(e4);
        return none;
    }
    
    public static <E extends Enum<E>> EnumSet<E> of(final E e, final E e2, final E e3, final E e4, final E e5) {
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.add(e);
        none.add(e2);
        none.add(e3);
        none.add(e4);
        none.add(e5);
        return none;
    }
    
    @SafeVarargs
    public static <E extends Enum<E>> EnumSet<E> of(final E e, final E... array) {
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.add(e);
        for (int length = array.length, i = 0; i < length; ++i) {
            none.add(array[i]);
        }
        return none;
    }
    
    public static <E extends Enum<E>> EnumSet<E> range(final E e, final E e2) {
        if (e.compareTo(e2) > 0) {
            throw new IllegalArgumentException(e + " > " + e2);
        }
        final EnumSet<E> none = noneOf(e.getDeclaringClass());
        none.addRange(e, e2);
        return none;
    }
    
    abstract void addRange(final E p0, final E p1);
    
    public EnumSet<E> clone() {
        try {
            return (EnumSet)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new AssertionError((Object)ex);
        }
    }
    
    abstract void complement();
    
    final void typeCheck(final E e) {
        final Class<? extends Enum> class1 = e.getClass();
        if (class1 != this.elementType && class1.getSuperclass() != this.elementType) {
            throw new ClassCastException(class1 + " != " + this.elementType);
        }
    }
    
    private static <E extends Enum<E>> E[] getUniverse(final Class<E> clazz) {
        return SharedSecrets.getJavaLangAccess().getEnumConstantsShared(clazz);
    }
    
    Object writeReplace() {
        return new SerializationProxy((EnumSet<Enum>)this);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
    
    static {
        EnumSet.ZERO_LENGTH_ENUM_ARRAY = (Enum<?>[])new Enum[0];
    }
    
    private static class SerializationProxy<E extends Enum<E>> implements Serializable
    {
        private final Class<E> elementType;
        private final Enum<?>[] elements;
        private static final long serialVersionUID = 362491234563181265L;
        
        SerializationProxy(final EnumSet<E> set) {
            this.elementType = set.elementType;
            this.elements = set.toArray(EnumSet.ZERO_LENGTH_ENUM_ARRAY);
        }
        
        private Object readResolve() {
            final EnumSet<E> none = EnumSet.noneOf(this.elementType);
            final Enum<?>[] elements = this.elements;
            for (int length = elements.length, i = 0; i < length; ++i) {
                none.add((E)elements[i]);
            }
            return none;
        }
    }
}
