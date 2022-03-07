package java.util;

import java.util.function.*;

public abstract static class AbstractIntSpliterator implements Spliterator.OfInt
{
    static final int MAX_BATCH = 33554432;
    static final int BATCH_UNIT = 1024;
    private final int characteristics;
    private long est;
    private int batch;
    
    protected AbstractIntSpliterator(final long est, final int n) {
        this.est = est;
        this.characteristics = (((n & 0x40) != 0x0) ? (n | 0x4000) : n);
    }
    
    @Override
    public Spliterator.OfInt trySplit() {
        final HoldingIntConsumer holdingIntConsumer = new HoldingIntConsumer();
        final long est = this.est;
        if (est > 1L && this.tryAdvance((IntConsumer)holdingIntConsumer)) {
            int n = this.batch + 1024;
            if (n > est) {
                n = (int)est;
            }
            if (n > 33554432) {
                n = 33554432;
            }
            final int[] array = new int[n];
            int batch = 0;
            do {
                array[batch] = holdingIntConsumer.value;
            } while (++batch < n && this.tryAdvance((IntConsumer)holdingIntConsumer));
            this.batch = batch;
            if (this.est != Long.MAX_VALUE) {
                this.est -= batch;
            }
            return new IntArraySpliterator(array, 0, batch, this.characteristics());
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
    
    static final class HoldingIntConsumer implements IntConsumer
    {
        int value;
        
        @Override
        public void accept(final int value) {
            this.value = value;
        }
    }
}
