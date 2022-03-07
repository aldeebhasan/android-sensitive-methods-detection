package java.util;

import java.util.function.*;

public interface OfLong extends OfPrimitive<Long, LongConsumer, OfLong>
{
    OfLong trySplit();
    
    boolean tryAdvance(final LongConsumer p0);
    
    default void forEachRemaining(final LongConsumer longConsumer) {
        while (this.tryAdvance(longConsumer)) {}
    }
    
    default boolean tryAdvance(final Consumer<? super Long> consumer) {
        if (consumer instanceof LongConsumer) {
            return this.tryAdvance((LongConsumer)consumer);
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
        }
        return this.tryAdvance(consumer::accept);
    }
    
    default void forEachRemaining(final Consumer<? super Long> consumer) {
        if (consumer instanceof LongConsumer) {
            this.forEachRemaining((LongConsumer)consumer);
        }
        else {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
            }
            this.forEachRemaining(consumer::accept);
        }
    }
}
