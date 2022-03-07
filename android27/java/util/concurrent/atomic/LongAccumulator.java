package java.util.concurrent.atomic;

import java.util.function.*;
import java.io.*;

public class LongAccumulator extends Striped64 implements Serializable
{
    private static final long serialVersionUID = 7249069246863182397L;
    private final LongBinaryOperator function;
    private final long identity;
    
    public LongAccumulator(final LongBinaryOperator function, final long n) {
        this.function = function;
        this.identity = n;
        this.base = n;
    }
    
    public void accumulate(final long n) {
        final Cell[] cells;
        final long base;
        final long applyAsLong;
        if ((cells = this.cells) != null || ((applyAsLong = this.function.applyAsLong(base = this.base, n)) != base && !this.casBase(base, applyAsLong))) {
            boolean b = true;
            final int n2;
            final Cell cell;
            final long value;
            final long applyAsLong2;
            if (cells == null || (n2 = cells.length - 1) < 0 || (cell = cells[getProbe() & n2]) == null || !(b = ((applyAsLong2 = this.function.applyAsLong(value = cell.value, n)) == value || cell.cas(value, applyAsLong2)))) {
                this.longAccumulate(n, this.function, b);
            }
        }
    }
    
    public long get() {
        final Cell[] cells = this.cells;
        long n = this.base;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    n = this.function.applyAsLong(n, cell.value);
                }
            }
        }
        return n;
    }
    
    public void reset() {
        final Cell[] cells = this.cells;
        this.base = this.identity;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    cell.value = this.identity;
                }
            }
        }
    }
    
    public long getThenReset() {
        final Cell[] cells = this.cells;
        long n = this.base;
        this.base = this.identity;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    final long value = cell.value;
                    cell.value = this.identity;
                    n = this.function.applyAsLong(n, value);
                }
            }
        }
        return n;
    }
    
    @Override
    public String toString() {
        return Long.toString(this.get());
    }
    
    @Override
    public long longValue() {
        return this.get();
    }
    
    @Override
    public int intValue() {
        return (int)this.get();
    }
    
    @Override
    public float floatValue() {
        return this.get();
    }
    
    @Override
    public double doubleValue() {
        return this.get();
    }
    
    private Object writeReplace() {
        return new SerializationProxy(this);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
    
    private static class SerializationProxy implements Serializable
    {
        private static final long serialVersionUID = 7249069246863182397L;
        private final long value;
        private final LongBinaryOperator function;
        private final long identity;
        
        SerializationProxy(final LongAccumulator longAccumulator) {
            this.function = longAccumulator.function;
            this.identity = longAccumulator.identity;
            this.value = longAccumulator.get();
        }
        
        private Object readResolve() {
            final LongAccumulator longAccumulator = new LongAccumulator(this.function, this.identity);
            longAccumulator.base = this.value;
            return longAccumulator;
        }
    }
}
