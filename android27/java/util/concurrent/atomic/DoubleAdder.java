package java.util.concurrent.atomic;

import java.util.function.*;
import java.io.*;

public class DoubleAdder extends Striped64 implements Serializable
{
    private static final long serialVersionUID = 7249069246863182397L;
    
    public void add(final double n) {
        final Cell[] cells;
        if ((cells = this.cells) == null) {
            final long base = this.base;
            if (this.casBase(base, Double.doubleToRawLongBits(Double.longBitsToDouble(base) + n))) {
                return;
            }
        }
        boolean cas = true;
        final int n2;
        final Cell cell;
        if (cells != null && (n2 = cells.length - 1) >= 0 && (cell = cells[getProbe() & n2]) != null) {
            final Cell cell2 = cell;
            final long value = cell.value;
            if (cas = cell2.cas(value, Double.doubleToRawLongBits(Double.longBitsToDouble(value) + n))) {
                return;
            }
        }
        this.doubleAccumulate(n, null, cas);
    }
    
    public double sum() {
        final Cell[] cells = this.cells;
        double longBitsToDouble = Double.longBitsToDouble(this.base);
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    longBitsToDouble += Double.longBitsToDouble(cell.value);
                }
            }
        }
        return longBitsToDouble;
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
    
    public double sumThenReset() {
        final Cell[] cells = this.cells;
        double longBitsToDouble = Double.longBitsToDouble(this.base);
        this.base = 0L;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    final long value = cell.value;
                    cell.value = 0L;
                    longBitsToDouble += Double.longBitsToDouble(value);
                }
            }
        }
        return longBitsToDouble;
    }
    
    @Override
    public String toString() {
        return Double.toString(this.sum());
    }
    
    @Override
    public double doubleValue() {
        return this.sum();
    }
    
    @Override
    public long longValue() {
        return (long)this.sum();
    }
    
    @Override
    public int intValue() {
        return (int)this.sum();
    }
    
    @Override
    public float floatValue() {
        return (float)this.sum();
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
        private final double value;
        
        SerializationProxy(final DoubleAdder doubleAdder) {
            this.value = doubleAdder.sum();
        }
        
        private Object readResolve() {
            final DoubleAdder doubleAdder = new DoubleAdder();
            doubleAdder.base = Double.doubleToRawLongBits(this.value);
            return doubleAdder;
        }
    }
}
