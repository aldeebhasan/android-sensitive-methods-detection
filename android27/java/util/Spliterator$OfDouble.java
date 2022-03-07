package java.util;

import java.util.function.*;

public interface OfDouble extends OfPrimitive<Double, DoubleConsumer, OfDouble>
{
    OfDouble trySplit();
    
    boolean tryAdvance(final DoubleConsumer p0);
    
    default void forEachRemaining(final DoubleConsumer doubleConsumer) {
        while (this.tryAdvance(doubleConsumer)) {}
    }
    
    default boolean tryAdvance(final Consumer<? super Double> consumer) {
        if (consumer instanceof DoubleConsumer) {
            return this.tryAdvance((DoubleConsumer)consumer);
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
        }
        return this.tryAdvance(consumer::accept);
    }
    
    default void forEachRemaining(final Consumer<? super Double> consumer) {
        if (consumer instanceof DoubleConsumer) {
            this.forEachRemaining((DoubleConsumer)consumer);
        }
        else {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
            }
            this.forEachRemaining(consumer::accept);
        }
    }
}
