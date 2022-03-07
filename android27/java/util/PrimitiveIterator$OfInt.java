package java.util;

import java.util.function.*;

public interface OfInt extends PrimitiveIterator<Integer, IntConsumer>
{
    int nextInt();
    
    default void forEachRemaining(final IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        while (super.hasNext()) {
            intConsumer.accept(this.nextInt());
        }
    }
    
    default Integer next() {
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfInt.nextInt()");
        }
        return this.nextInt();
    }
    
    default void forEachRemaining(final Consumer<? super Integer> consumer) {
        if (consumer instanceof IntConsumer) {
            this.forEachRemaining((IntConsumer)consumer);
        }
        else {
            Objects.requireNonNull((Object)consumer);
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
            }
            this.forEachRemaining(consumer::accept);
        }
    }
}
