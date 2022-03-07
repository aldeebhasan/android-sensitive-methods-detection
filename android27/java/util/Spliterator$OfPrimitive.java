package java.util;

public interface OfPrimitive<T, T_CONS, T_SPLITR extends OfPrimitive<T, T_CONS, T_SPLITR>> extends Spliterator<T>
{
    T_SPLITR trySplit();
    
    boolean tryAdvance(final T_CONS p0);
    
    default void forEachRemaining(final T_CONS t_CONS) {
        while (this.tryAdvance(t_CONS)) {}
    }
}
