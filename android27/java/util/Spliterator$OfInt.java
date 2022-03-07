package java.util;

import java.util.function.*;

public interface OfInt extends OfPrimitive<Integer, IntConsumer, OfInt>
{
    OfInt trySplit();
    
    boolean tryAdvance(final IntConsumer p0);
    
    default void forEachRemaining(final IntConsumer intConsumer) {
        while (this.tryAdvance(intConsumer)) {}
    }
    
    default boolean tryAdvance(final Consumer<? super Integer> consumer) {
        if (consumer instanceof IntConsumer) {
            return this.tryAdvance((IntConsumer)consumer);
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
        }
        return this.tryAdvance(consumer::accept);
    }
    
    default void forEachRemaining(final Consumer<? super Integer> consumer) {
        if (consumer instanceof IntConsumer) {
            this.forEachRemaining((IntConsumer)consumer);
        }
        else {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
            }
            this.forEachRemaining(consumer::accept);
        }
    }
}
