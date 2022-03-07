package java.util;

import java.util.concurrent.atomic.*;
import sun.misc.*;
import java.util.stream.*;
import java.io.*;
import java.util.function.*;

public class Random implements Serializable
{
    static final long serialVersionUID = 3905348978240129619L;
    private final AtomicLong seed;
    private static final long multiplier = 25214903917L;
    private static final long addend = 11L;
    private static final long mask = 281474976710655L;
    private static final double DOUBLE_UNIT = 1.1102230246251565E-16;
    static final String BadBound = "bound must be positive";
    static final String BadRange = "bound must be greater than origin";
    static final String BadSize = "size must be non-negative";
    private static final AtomicLong seedUniquifier;
    private double nextNextGaussian;
    private boolean haveNextNextGaussian;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final Unsafe unsafe;
    private static final long seedOffset;
    
    public Random() {
        this(seedUniquifier() ^ System.nanoTime());
    }
    
    private static long seedUniquifier() {
        long value;
        long n;
        do {
            value = Random.seedUniquifier.get();
            n = value * 181783497276652981L;
        } while (!Random.seedUniquifier.compareAndSet(value, n));
        return n;
    }
    
    public Random(final long seed) {
        this.haveNextNextGaussian = false;
        if (this.getClass() == Random.class) {
            this.seed = new AtomicLong(initialScramble(seed));
        }
        else {
            this.seed = new AtomicLong();
            this.setSeed(seed);
        }
    }
    
    private static long initialScramble(final long n) {
        return (n ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
    }
    
    public synchronized void setSeed(final long n) {
        this.seed.set(initialScramble(n));
        this.haveNextNextGaussian = false;
    }
    
    protected int next(final int n) {
        final AtomicLong seed = this.seed;
        long value;
        long n2;
        do {
            value = seed.get();
            n2 = (value * 25214903917L + 11L & 0xFFFFFFFFFFFFL);
        } while (!seed.compareAndSet(value, n2));
        return (int)(n2 >>> 48 - n);
    }
    
    public void nextBytes(final byte[] array) {
        int i = 0;
        final int length = array.length;
        while (i < length) {
            int nextInt = this.nextInt();
            int min = Math.min(length - i, 4);
            while (min-- > 0) {
                array[i++] = (byte)nextInt;
                nextInt >>= 8;
            }
        }
    }
    
    final long internalNextLong(final long n, final long n2) {
        long n3 = this.nextLong();
        if (n < n2) {
            final long n4 = n2 - n;
            final long n5 = n4 - 1L;
            if ((n4 & n5) == 0x0L) {
                n3 = (n3 & n5) + n;
            }
            else if (n4 > 0L) {
                long n7;
                for (long n6 = n3 >>> 1; n6 + n5 - (n7 = n6 % n4) < 0L; n6 = this.nextLong() >>> 1) {}
                n3 = n7 + n;
            }
            else {
                while (n3 < n || n3 >= n2) {
                    n3 = this.nextLong();
                }
            }
        }
        return n3;
    }
    
    final int internalNextInt(final int n, final int n2) {
        if (n >= n2) {
            return this.nextInt();
        }
        final int n3 = n2 - n;
        if (n3 > 0) {
            return this.nextInt(n3) + n;
        }
        int nextInt;
        do {
            nextInt = this.nextInt();
        } while (nextInt < n || nextInt >= n2);
        return nextInt;
    }
    
    final double internalNextDouble(final double n, final double n2) {
        double n3 = this.nextDouble();
        if (n < n2) {
            n3 = n3 * (n2 - n) + n;
            if (n3 >= n2) {
                n3 = Double.longBitsToDouble(Double.doubleToLongBits(n2) - 1L);
            }
        }
        return n3;
    }
    
    public int nextInt() {
        return this.next(32);
    }
    
    public int nextInt(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        final int next = this.next(31);
        final int n2 = n - 1;
        int n3;
        if ((n & n2) == 0x0) {
            n3 = n * next >> 31;
        }
        else {
            for (int next2 = next; next2 - (n3 = next2 % n) + n2 < 0; next2 = this.next(31)) {}
        }
        return n3;
    }
    
    public long nextLong() {
        return (this.next(32) << 32) + this.next(32);
    }
    
    public boolean nextBoolean() {
        return this.next(1) != 0;
    }
    
    public float nextFloat() {
        return this.next(24) / 1.6777216E7f;
    }
    
    public double nextDouble() {
        return ((this.next(26) << 27) + this.next(27)) * 1.1102230246251565E-16;
    }
    
    public synchronized double nextGaussian() {
        if (this.haveNextNextGaussian) {
            this.haveNextNextGaussian = false;
            return this.nextNextGaussian;
        }
        double n;
        double n2;
        double n3;
        do {
            n2 = 2.0 * this.nextDouble() - 1.0;
            n3 = 2.0 * this.nextDouble() - 1.0;
            n = n2 * n2 + n3 * n3;
        } while (n >= 1.0 || n == 0.0);
        final double sqrt = StrictMath.sqrt(-2.0 * StrictMath.log(n) / n);
        this.nextNextGaussian = n3 * sqrt;
        this.haveNextNextGaussian = true;
        return n2 * sqrt;
    }
    
    public IntStream ints(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, n, Integer.MAX_VALUE, 0), false);
    }
    
    public IntStream ints() {
        return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
    }
    
    public IntStream ints(final long n, final int n2, final int n3) {
        if (n < 0L) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (n2 >= n3) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, n, n2, n3), false);
    }
    
    public IntStream ints(final int n, final int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, n, n2), false);
    }
    
    public LongStream longs(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, n, Long.MAX_VALUE, 0L), false);
    }
    
    public LongStream longs() {
        return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
    }
    
    public LongStream longs(final long n, final long n2, final long n3) {
        if (n < 0L) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (n2 >= n3) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, n, n2, n3), false);
    }
    
    public LongStream longs(final long n, final long n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, n, n2), false);
    }
    
    public DoubleStream doubles(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, n, Double.MAX_VALUE, 0.0), false);
    }
    
    public DoubleStream doubles() {
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0), false);
    }
    
    public DoubleStream doubles(final long n, final double n2, final double n3) {
        if (n < 0L) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (n2 >= n3) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, n, n2, n3), false);
    }
    
    public DoubleStream doubles(final double n, final double n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, n, n2), false);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final long value = fields.get("seed", -1L);
        if (value < 0L) {
            throw new StreamCorruptedException("Random: invalid seed");
        }
        this.resetSeed(value);
        this.nextNextGaussian = fields.get("nextNextGaussian", 0.0);
        this.haveNextNextGaussian = fields.get("haveNextNextGaussian", false);
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("seed", this.seed.get());
        putFields.put("nextNextGaussian", this.nextNextGaussian);
        putFields.put("haveNextNextGaussian", this.haveNextNextGaussian);
        objectOutputStream.writeFields();
    }
    
    private void resetSeed(final long n) {
        Random.unsafe.putObjectVolatile(this, Random.seedOffset, new AtomicLong(n));
    }
    
    static {
        seedUniquifier = new AtomicLong(8682522807148012L);
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("seed", Long.TYPE), new ObjectStreamField("nextNextGaussian", Double.TYPE), new ObjectStreamField("haveNextNextGaussian", Boolean.TYPE) };
        unsafe = Unsafe.getUnsafe();
        try {
            seedOffset = Random.unsafe.objectFieldOffset(Random.class.getDeclaredField("seed"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class RandomDoublesSpliterator implements Spliterator.OfDouble
    {
        final Random rng;
        long index;
        final long fence;
        final double origin;
        final double bound;
        
        RandomDoublesSpliterator(final Random rng, final long index, final long fence, final double origin, final double bound) {
            this.rng = rng;
            this.index = index;
            this.fence = fence;
            this.origin = origin;
            this.bound = bound;
        }
        
        @Override
        public RandomDoublesSpliterator trySplit() {
            final long index = this.index;
            final long index2 = index + this.fence >>> 1;
            return (index2 <= index) ? null : new RandomDoublesSpliterator(this.rng, index, this.index = index2, this.origin, this.bound);
        }
        
        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }
        
        @Override
        public int characteristics() {
            return 17728;
        }
        
        @Override
        public boolean tryAdvance(final DoubleConsumer doubleConsumer) {
            if (doubleConsumer == null) {
                throw new NullPointerException();
            }
            final long index = this.index;
            if (index < this.fence) {
                doubleConsumer.accept(this.rng.internalNextDouble(this.origin, this.bound));
                this.index = index + 1L;
                return true;
            }
            return false;
        }
        
        @Override
        public void forEachRemaining(final DoubleConsumer doubleConsumer) {
            if (doubleConsumer == null) {
                throw new NullPointerException();
            }
            long index = this.index;
            final long fence = this.fence;
            if (index < fence) {
                this.index = fence;
                final Random rng = this.rng;
                final double origin = this.origin;
                final double bound = this.bound;
                do {
                    doubleConsumer.accept(rng.internalNextDouble(origin, bound));
                } while (++index < fence);
            }
        }
    }
    
    static final class RandomIntsSpliterator implements Spliterator.OfInt
    {
        final Random rng;
        long index;
        final long fence;
        final int origin;
        final int bound;
        
        RandomIntsSpliterator(final Random rng, final long index, final long fence, final int origin, final int bound) {
            this.rng = rng;
            this.index = index;
            this.fence = fence;
            this.origin = origin;
            this.bound = bound;
        }
        
        @Override
        public RandomIntsSpliterator trySplit() {
            final long index = this.index;
            final long index2 = index + this.fence >>> 1;
            return (index2 <= index) ? null : new RandomIntsSpliterator(this.rng, index, this.index = index2, this.origin, this.bound);
        }
        
        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }
        
        @Override
        public int characteristics() {
            return 17728;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (intConsumer == null) {
                throw new NullPointerException();
            }
            final long index = this.index;
            if (index < this.fence) {
                intConsumer.accept(this.rng.internalNextInt(this.origin, this.bound));
                this.index = index + 1L;
                return true;
            }
            return false;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            if (intConsumer == null) {
                throw new NullPointerException();
            }
            long index = this.index;
            final long fence = this.fence;
            if (index < fence) {
                this.index = fence;
                final Random rng = this.rng;
                final int origin = this.origin;
                final int bound = this.bound;
                do {
                    intConsumer.accept(rng.internalNextInt(origin, bound));
                } while (++index < fence);
            }
        }
    }
    
    static final class RandomLongsSpliterator implements Spliterator.OfLong
    {
        final Random rng;
        long index;
        final long fence;
        final long origin;
        final long bound;
        
        RandomLongsSpliterator(final Random rng, final long index, final long fence, final long origin, final long bound) {
            this.rng = rng;
            this.index = index;
            this.fence = fence;
            this.origin = origin;
            this.bound = bound;
        }
        
        @Override
        public RandomLongsSpliterator trySplit() {
            final long index = this.index;
            final long index2 = index + this.fence >>> 1;
            return (index2 <= index) ? null : new RandomLongsSpliterator(this.rng, index, this.index = index2, this.origin, this.bound);
        }
        
        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }
        
        @Override
        public int characteristics() {
            return 17728;
        }
        
        @Override
        public boolean tryAdvance(final LongConsumer longConsumer) {
            if (longConsumer == null) {
                throw new NullPointerException();
            }
            final long index = this.index;
            if (index < this.fence) {
                longConsumer.accept(this.rng.internalNextLong(this.origin, this.bound));
                this.index = index + 1L;
                return true;
            }
            return false;
        }
        
        @Override
        public void forEachRemaining(final LongConsumer longConsumer) {
            if (longConsumer == null) {
                throw new NullPointerException();
            }
            long index = this.index;
            final long fence = this.fence;
            if (index < fence) {
                this.index = fence;
                final Random rng = this.rng;
                final long origin = this.origin;
                final long bound = this.bound;
                do {
                    longConsumer.accept(rng.internalNextLong(origin, bound));
                } while (++index < fence);
            }
        }
    }
}
