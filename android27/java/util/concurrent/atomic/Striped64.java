package java.util.concurrent.atomic;

import java.util.concurrent.*;
import java.util.function.*;
import sun.misc.*;

abstract class Striped64 extends Number
{
    static final int NCPU;
    transient volatile Cell[] cells;
    transient volatile long base;
    transient volatile int cellsBusy;
    private static final Unsafe UNSAFE;
    private static final long BASE;
    private static final long CELLSBUSY;
    private static final long PROBE;
    
    final boolean casBase(final long n, final long n2) {
        return Striped64.UNSAFE.compareAndSwapLong(this, Striped64.BASE, n, n2);
    }
    
    final boolean casCellsBusy() {
        return Striped64.UNSAFE.compareAndSwapInt(this, Striped64.CELLSBUSY, 0, 1);
    }
    
    static final int getProbe() {
        return Striped64.UNSAFE.getInt(Thread.currentThread(), Striped64.PROBE);
    }
    
    static final int advanceProbe(int n) {
        n ^= n << 13;
        n ^= n >>> 17;
        n ^= n << 5;
        Striped64.UNSAFE.putInt(Thread.currentThread(), Striped64.PROBE, n);
        return n;
    }
    
    final void longAccumulate(final long n, final LongBinaryOperator longBinaryOperator, boolean b) {
        int n2;
        if ((n2 = getProbe()) == 0) {
            ThreadLocalRandom.current();
            n2 = getProbe();
            b = true;
        }
        int n3 = 0;
        while (true) {
            final Cell[] cells;
            final int length;
            if ((cells = this.cells) != null && (length = cells.length) > 0) {
                final Cell cell;
                if ((cell = cells[length - 1 & n2]) == null) {
                    if (this.cellsBusy == 0) {
                        final Cell cell2 = new Cell(n);
                        if (this.cellsBusy == 0 && this.casCellsBusy()) {
                            boolean b2 = false;
                            try {
                                final Cell[] cells2;
                                final int length2;
                                final int n4;
                                if ((cells2 = this.cells) != null && (length2 = cells2.length) > 0 && cells2[n4 = (length2 - 1 & n2)] == null) {
                                    cells2[n4] = cell2;
                                    b2 = true;
                                }
                            }
                            finally {
                                this.cellsBusy = 0;
                            }
                            if (b2) {
                                break;
                            }
                            continue;
                        }
                    }
                    n3 = 0;
                }
                else if (!b) {
                    b = true;
                }
                else {
                    final long value;
                    if (cell.cas(value = cell.value, (longBinaryOperator == null) ? (value + n) : longBinaryOperator.applyAsLong(value, n))) {
                        break;
                    }
                    if (length >= Striped64.NCPU || this.cells != cells) {
                        n3 = 0;
                    }
                    else if (n3 == 0) {
                        n3 = 1;
                    }
                    else if (this.cellsBusy == 0 && this.casCellsBusy()) {
                        try {
                            if (this.cells == cells) {
                                final Cell[] cells3 = new Cell[length << 1];
                                for (int i = 0; i < length; ++i) {
                                    cells3[i] = cells[i];
                                }
                                this.cells = cells3;
                            }
                        }
                        finally {
                            this.cellsBusy = 0;
                        }
                        n3 = 0;
                        continue;
                    }
                }
                n2 = advanceProbe(n2);
            }
            else if (this.cellsBusy == 0 && this.cells == cells && this.casCellsBusy()) {
                boolean b3 = false;
                try {
                    if (this.cells == cells) {
                        final Cell[] cells4 = new Cell[2];
                        cells4[n2 & 0x1] = new Cell(n);
                        this.cells = cells4;
                        b3 = true;
                    }
                }
                finally {
                    this.cellsBusy = 0;
                }
                if (b3) {
                    break;
                }
                continue;
            }
            else {
                final long base;
                if (this.casBase(base = this.base, (longBinaryOperator == null) ? (base + n) : longBinaryOperator.applyAsLong(base, n))) {
                    break;
                }
                continue;
            }
        }
    }
    
    final void doubleAccumulate(final double n, final DoubleBinaryOperator doubleBinaryOperator, boolean b) {
        int n2;
        if ((n2 = getProbe()) == 0) {
            ThreadLocalRandom.current();
            n2 = getProbe();
            b = true;
        }
        int n3 = 0;
        while (true) {
            final Cell[] cells;
            final int length;
            if ((cells = this.cells) != null && (length = cells.length) > 0) {
                final Cell cell;
                if ((cell = cells[length - 1 & n2]) == null) {
                    if (this.cellsBusy == 0) {
                        final Cell cell2 = new Cell(Double.doubleToRawLongBits(n));
                        if (this.cellsBusy == 0 && this.casCellsBusy()) {
                            boolean b2 = false;
                            try {
                                final Cell[] cells2;
                                final int length2;
                                final int n4;
                                if ((cells2 = this.cells) != null && (length2 = cells2.length) > 0 && cells2[n4 = (length2 - 1 & n2)] == null) {
                                    cells2[n4] = cell2;
                                    b2 = true;
                                }
                            }
                            finally {
                                this.cellsBusy = 0;
                            }
                            if (b2) {
                                break;
                            }
                            continue;
                        }
                    }
                    n3 = 0;
                }
                else if (!b) {
                    b = true;
                }
                else {
                    final long value;
                    if (cell.cas(value = cell.value, (doubleBinaryOperator == null) ? Double.doubleToRawLongBits(Double.longBitsToDouble(value) + n) : Double.doubleToRawLongBits(doubleBinaryOperator.applyAsDouble(Double.longBitsToDouble(value), n)))) {
                        break;
                    }
                    if (length >= Striped64.NCPU || this.cells != cells) {
                        n3 = 0;
                    }
                    else if (n3 == 0) {
                        n3 = 1;
                    }
                    else if (this.cellsBusy == 0 && this.casCellsBusy()) {
                        try {
                            if (this.cells == cells) {
                                final Cell[] cells3 = new Cell[length << 1];
                                for (int i = 0; i < length; ++i) {
                                    cells3[i] = cells[i];
                                }
                                this.cells = cells3;
                            }
                        }
                        finally {
                            this.cellsBusy = 0;
                        }
                        n3 = 0;
                        continue;
                    }
                }
                n2 = advanceProbe(n2);
            }
            else if (this.cellsBusy == 0 && this.cells == cells && this.casCellsBusy()) {
                boolean b3 = false;
                try {
                    if (this.cells == cells) {
                        final Cell[] cells4 = new Cell[2];
                        cells4[n2 & 0x1] = new Cell(Double.doubleToRawLongBits(n));
                        this.cells = cells4;
                        b3 = true;
                    }
                }
                finally {
                    this.cellsBusy = 0;
                }
                if (b3) {
                    break;
                }
                continue;
            }
            else {
                final long base;
                if (this.casBase(base = this.base, (doubleBinaryOperator == null) ? Double.doubleToRawLongBits(Double.longBitsToDouble(base) + n) : Double.doubleToRawLongBits(doubleBinaryOperator.applyAsDouble(Double.longBitsToDouble(base), n)))) {
                    break;
                }
                continue;
            }
        }
    }
    
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        try {
            UNSAFE = Unsafe.getUnsafe();
            final Class<Striped64> clazz = Striped64.class;
            BASE = Striped64.UNSAFE.objectFieldOffset(clazz.getDeclaredField("base"));
            CELLSBUSY = Striped64.UNSAFE.objectFieldOffset(clazz.getDeclaredField("cellsBusy"));
            PROBE = Striped64.UNSAFE.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomProbe"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    @Contended
    static final class Cell
    {
        volatile long value;
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        
        Cell(final long value) {
            this.value = value;
        }
        
        final boolean cas(final long n, final long n2) {
            return Cell.UNSAFE.compareAndSwapLong(this, Cell.valueOffset, n, n2);
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                valueOffset = Cell.UNSAFE.objectFieldOffset(Cell.class.getDeclaredField("value"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
}
