package java.util;

import java.util.function.*;

public abstract static class AbstractDoubleSpliterator implements Spliterator.OfDouble
{
    static final int MAX_BATCH = 33554432;
    static final int BATCH_UNIT = 1024;
    private final int characteristics;
    private long est;
    private int batch;
    
    protected AbstractDoubleSpliterator(final long est, final int n) {
        this.est = est;
        this.characteristics = (((n & 0x40) != 0x0) ? (n | 0x4000) : n);
    }
    
    @Override
    public Spliterator.OfDouble trySplit() {
        final HoldingDoubleConsumer holdingDoubleConsumer = new HoldingDoubleConsumer();
        final long est = this.est;
        if (est > 1L && this.tryAdvance((DoubleConsumer)holdingDoubleConsumer)) {
            int n = this.batch + 1024;
            if (n > est) {
                n = (int)est;
            }
            if (n > 33554432) {
                n = 33554432;
            }
            final double[] array = new double[n];
            int batch = 0;
            do {
                array[batch] = holdingDoubleConsumer.value;
            } while (++batch < n && this.tryAdvance((DoubleConsumer)holdingDoubleConsumer));
            this.batch = batch;
            if (this.est != Long.MAX_VALUE) {
                this.est -= batch;
            }
            return new DoubleArraySpliterator(array, 0, batch, this.characteristics());
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
    
    static final class HoldingDoubleConsumer implements DoubleConsumer
    {
        double value;
        
        @Override
        public void accept(final double value) {
            this.value = value;
        }
    }
}
