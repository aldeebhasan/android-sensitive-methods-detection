package java.util;

import java.util.function.*;

public interface OfLong extends PrimitiveIterator<Long, LongConsumer>
{
    long nextLong();
    
    default void forEachRemaining(final LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        while (super.hasNext()) {
            longConsumer.accept(this.nextLong());
        }
    }
    
    default Long next() {
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfLong.nextLong()");
        }
        return this.nextLong();
    }
    
    default void forEachRemaining(final Consumer<? super Long> consumer) {
        if (consumer instanceof LongConsumer) {
            this.forEachRemaining((LongConsumer)consumer);
        }
        else {
            Objects.requireNonNull((Object)consumer);
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
            }
            this.forEachRemaining(consumer::accept);
        }
    }
}
