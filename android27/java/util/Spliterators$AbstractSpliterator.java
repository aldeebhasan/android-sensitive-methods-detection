package java.util;

import java.util.function.*;

public abstract static class AbstractSpliterator<T> implements Spliterator<T>
{
    static final int BATCH_UNIT = 1024;
    static final int MAX_BATCH = 33554432;
    private final int characteristics;
    private long est;
    private int batch;
    
    protected AbstractSpliterator(final long est, final int n) {
        this.est = est;
        this.characteristics = (((n & 0x40) != 0x0) ? (n | 0x4000) : n);
    }
    
    @Override
    public Spliterator<T> trySplit() {
        final HoldingConsumer<Object> holdingConsumer = new HoldingConsumer<Object>();
        final long est = this.est;
        if (est > 1L && this.tryAdvance(holdingConsumer)) {
            int n = this.batch + 1024;
            if (n > est) {
                n = (int)est;
            }
            if (n > 33554432) {
                n = 33554432;
            }
            final Object[] array = new Object[n];
            int batch = 0;
            do {
                array[batch] = holdingConsumer.value;
            } while (++batch < n && this.tryAdvance(holdingConsumer));
            this.batch = batch;
            if (this.est != Long.MAX_VALUE) {
                this.est -= batch;
            }
            return new ArraySpliterator<T>(array, 0, batch, this.characteristics());
        }
        return null;
    }
    
    @Override
    public long estimateSize() {
        return this.est;
    }
    
    @Override
    public int characteristics() {
        return this.characteristics;
    }
    
    static final class HoldingConsumer<T> implements Consumer<T>
    {
        Object value;
        
        @Override
        public void accept(final T value) {
            this.value = value;
        }
    }
}
