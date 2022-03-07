package java.util;

import java.util.function.*;

public abstract static class AbstractLongSpliterator implements Spliterator.OfLong
{
    static final int MAX_BATCH = 33554432;
    static final int BATCH_UNIT = 1024;
    private final int characteristics;
    private long est;
    private int batch;
    
    protected AbstractLongSpliterator(final long est, final int n) {
        this.est = est;
        this.characteristics = (((n & 0x40) != 0x0) ? (n | 0x4000) : n);
    }
    
    @Override
    public Spliterator.OfLong trySplit() {
        final HoldingLongConsumer holdingLongConsumer = new HoldingLongConsumer();
        final long est = this.est;
        if (est > 1L && this.tryAdvance((LongConsumer)holdingLongConsumer)) {
            int n = this.batch + 1024;
            if (n > est) {
                n = (int)est;
            }
            if (n > 33554432) {
                n = 33554432;
            }
            final long[] array = new long[n];
            int batch = 0;
            do {
                array[batch] = holdingLongConsumer.value;
            } while (++batch < n && this.tryAdvance((LongConsumer)holdingLongConsumer));
            this.batch = batch;
            if (this.est != Long.MAX_VALUE) {
                this.est -= batch;
            }
            return new LongArraySpliterator(array, 0, batch, this.characteristics());
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
    
    static final class HoldingLongConsumer implements LongConsumer
    {
        long value;
        
        @Override
        public void accept(final long value) {
            this.value = value;
        }
    }
}
