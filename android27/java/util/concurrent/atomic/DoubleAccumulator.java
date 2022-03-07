package java.util.concurrent.atomic;

import java.util.function.*;
import java.io.*;

public class DoubleAccumulator extends Striped64 implements Serializable
{
    private static final long serialVersionUID = 7249069246863182397L;
    private final DoubleBinaryOperator function;
    private final long identity;
    
    public DoubleAccumulator(final DoubleBinaryOperator function, final double n) {
        this.function = function;
        final long doubleToRawLongBits = Double.doubleToRawLongBits(n);
        this.identity = doubleToRawLongBits;
        this.base = doubleToRawLongBits;
    }
    
    public void accumulate(final double n) {
        final Cell[] cells;
        final long base;
        final long doubleToRawLongBits;
        if ((cells = this.cells) != null || ((doubleToRawLongBits = Double.doubleToRawLongBits(this.function.applyAsDouble(Double.longBitsToDouble(base = this.base), n))) != base && !this.casBase(base, doubleToRawLongBits))) {
            boolean b = true;
            final int n2;
            final Cell cell;
            final long value;
            final long doubleToRawLongBits2;
            if (cells == null || (n2 = cells.length - 1) < 0 || (cell = cells[getProbe() & n2]) == null || !(b = ((doubleToRawLongBits2 = Double.doubleToRawLongBits(this.function.applyAsDouble(Double.longBitsToDouble(value = cell.value), n))) == value || cell.cas(value, doubleToRawLongBits2)))) {
                this.doubleAccumulate(n, this.function, b);
            }
        }
    }
    
    public double get() {
        final Cell[] cells = this.cells;
        double n = Double.longBitsToDouble(this.base);
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    n = this.function.applyAsDouble(n, Double.longBitsToDouble(cell.value));
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
    
    public double getThenReset() {
        final Cell[] cells = this.cells;
        double n = Double.longBitsToDouble(this.base);
        this.base = this.identity;
        if (cells != null) {
            for (int i = 0; i < cells.length; ++i) {
                final Cell cell;
                if ((cell = cells[i]) != null) {
                    final double longBitsToDouble = Double.longBitsToDouble(cell.value);
                    cell.value = this.identity;
                    n = this.function.applyAsDouble(n, longBitsToDouble);
                }
            }
        }
        return n;
    }
    
    @Override
    public String toString() {
        return Double.toString(this.get());
    }
    
    @Override
    public double doubleValue() {
        return this.get();
    }
    
    @Override
    public long longValue() {
        return (long)this.get();
    }
    
    @Override
    public int intValue() {
        return (int)this.get();
    }
    
    @Override
    public float floatValue() {
        return (float)this.get();
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
        private final DoubleBinaryOperator function;
        private final long identity;
        
        SerializationProxy(final DoubleAccumulator doubleAccumulator) {
            this.function = doubleAccumulator.function;
            this.identity = doubleAccumulator.identity;
            this.value = doubleAccumulator.get();
        }
        
        private Object readResolve() {
            final DoubleAccumulator doubleAccumulator = new DoubleAccumulator(this.function, Double.longBitsToDouble(this.identity));
            doubleAccumulator.base = Double.doubleToRawLongBits(this.value);
            return doubleAccumulator;
        }
    }
}
