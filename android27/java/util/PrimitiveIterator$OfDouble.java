package java.util;

import java.util.function.*;

public interface OfDouble extends PrimitiveIterator<Double, DoubleConsumer>
{
    double nextDouble();
    
    default void forEachRemaining(final DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        while (super.hasNext()) {
            doubleConsumer.accept(this.nextDouble());
        }
    }
    
    default Double next() {
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfDouble.nextLong()");
        }
        return this.nextDouble();
    }
    
    default void forEachRemaining(final Consumer<? super Double> consumer) {
        if (consumer instanceof DoubleConsumer) {
            this.forEachRemaining((DoubleConsumer)consumer);
        }
        else {
            Objects.requireNonNull((Object)consumer);
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
            }
            this.forEachRemaining(consumer::accept);
        }
    }
}
