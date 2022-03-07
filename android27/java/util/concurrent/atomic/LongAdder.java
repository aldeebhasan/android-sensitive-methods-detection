package java.util.concurrent.atomic;

import java.util.function.*;
import java.io.*;

public class LongAdder extends Striped64 implements Serializable
{
    private static final long serialVersionUID = 7249069246863182397L;
    
    public void add(final long n) {
        final Cell[] cells;
        if ((cells = this.cells) == null) {
            final long base = this.base;
            if (this.casBase(base, base + n)) {
                return;
            }
        }
        boolean cas = true;
        final int n2;
        final Cell cell;
        if (cells != null && (n2 = cells.length - 1) >= 0 && (cell = cells[getProbe() & n2]) != null) {
            final Cell cell2 = cell;
            final long value = cell.value;
            if (cas = cell2.cas(value, value + n)) {
                return;
            }
        }
        this.longAccumulate(n, null, cas);
    }
    
    public void increment() {
        this.add(1L);
    }
    
    public void decrement() {
        this.add(-1L);
    }
    
    public long sum() {
        final Cell[] cells = this.cells;
        long base = this.base;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    base += cell.value;
                }
            }
        }
        return base;
    }
    
    public void reset() {
        final Cell[] cells = this.cells;
        this.base = 0L;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    cell.value = 0L;
                }
            }
        }
    }
    
    public long sumThenReset() {
        final Cell[] cells = this.cells;
        long base = this.base;
        this.base = 0L;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    base += cell.value;
                    cell.value = 0L;
                }
            }
        }
        return base;
    }
    
    @Override
    public String toString() {
        return Long.toString(this.sum());
    }
    
    @Override
    public long longValue() {
        return this.sum();
    }
    
    @Override
    public int intValue() {
        return (int)this.sum();
    }
    
    @Override
    public float floatValue() {
        return this.sum();
    }
    
    @Override
    public double doubleValue() {
        return this.sum();
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
        
        SerializationProxy(final LongAdder longAdder) {
            this.value = longAdder.sum();
        }
        
        private Object readResolve() {
            final LongAdder longAdder = new LongAdder();
            longAdder.base = this.value;
            return longAdder;
        }
    }
}
