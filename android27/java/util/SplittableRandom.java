package java.util;

import java.util.concurrent.atomic.*;
import sun.security.action.*;
import java.security.*;
import java.util.stream.*;
import java.util.function.*;

public final class SplittableRandom
{
    private static final long GOLDEN_GAMMA = -7046029254386353131L;
    private static final double DOUBLE_UNIT = 1.1102230246251565E-16;
    private long seed;
    private final long gamma;
    private static final AtomicLong defaultGen;
    static final String BadBound = "bound must be positive";
    static final String BadRange = "bound must be greater than origin";
    static final String BadSize = "size must be non-negative";
    
    private SplittableRandom(final long seed, final long gamma) {
        this.seed = seed;
        this.gamma = gamma;
    }
    
    private static long mix64(long n) {
        n = (n ^ n >>> 30) * -4658895280553007687L;
        n = (n ^ n >>> 27) * -7723592293110705685L;
        return n ^ n >>> 31;
    }
    
    private static int mix32(long n) {
        n = (n ^ n >>> 33) * 7109453100751455733L;
        return (int)((n ^ n >>> 28) * -3808689974395783757L >>> 32);
    }
    
    private static long mixGamma(long n) {
        n = (n ^ n >>> 33) * -49064778989728563L;
        n = (n ^ n >>> 33) * -4265267296055464877L;
        n = ((n ^ n >>> 33) | 0x1L);
        return (Long.bitCount(n ^ n >>> 1) < 24) ? (n ^ 0xAAAAAAAAAAAAAAAAL) : n;
    }
    
    private long nextSeed() {
        return this.seed += this.gamma;
    }
    
    private static long initialSeed() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.util.secureRandomSeed"));
        if (s != null && s.equalsIgnoreCase("true")) {
            final byte[] seed = SecureRandom.getSeed(8);
            long n = seed[0] & 0xFFL;
            for (int i = 1; i < 8; ++i) {
                n = (n << 8 | (seed[i] & 0xFFL));
            }
            return n;
        }
        return mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
    }
    
    final long internalNextLong(final long n, final long n2) {
        long n3 = mix64(this.nextSeed());
        if (n < n2) {
            final long n4 = n2 - n;
            final long n5 = n4 - 1L;
            if ((n4 & n5) == 0x0L) {
                n3 = (n3 & n5) + n;
            }
            else if (n4 > 0L) {
                long n7;
                for (long n6 = n3 >>> 1; n6 + n5 - (n7 = n6 % n4) < 0L; n6 = mix64(this.nextSeed()) >>> 1) {}
                n3 = n7 + n;
            }
            else {
                while (n3 < n || n3 >= n2) {
                    n3 = mix64(this.nextSeed());
                }
            }
        }
        return n3;
    }
    
    final int internalNextInt(final int n, final int n2) {
        int n3 = mix32(this.nextSeed());
        if (n < n2) {
            final int n4 = n2 - n;
            final int n5 = n4 - 1;
            if ((n4 & n5) == 0x0) {
                n3 = (n3 & n5) + n;
            }
            else if (n4 > 0) {
                int n7;
                for (int n6 = n3 >>> 1; n6 + n5 - (n7 = n6 % n4) < 0; n6 = mix32(this.nextSeed()) >>> 1) {}
                n3 = n7 + n;
            }
            else {
                while (n3 < n || n3 >= n2) {
                    n3 = mix32(this.nextSeed());
                }
            }
        }
        return n3;
    }
    
    final double internalNextDouble(final double n, final double n2) {
        double longBitsToDouble = (this.nextLong() >>> 11) * 1.1102230246251565E-16;
        if (n < n2) {
            longBitsToDouble = longBitsToDouble * (n2 - n) + n;
            if (longBitsToDouble >= n2) {
                longBitsToDouble = Double.longBitsToDouble(Double.doubleToLongBits(n2) - 1L);
            }
        }
        return longBitsToDouble;
    }
    
    public SplittableRandom(final long n) {
        this(n, -7046029254386353131L);
    }
    
    public SplittableRandom() {
        final long andAdd = SplittableRandom.defaultGen.getAndAdd(4354685564936845354L);
        this.seed = mix64(andAdd);
        this.gamma = mixGamma(andAdd - 7046029254386353131L);
    }
    
    public SplittableRandom split() {
        return new SplittableRandom(this.nextLong(), mixGamma(this.nextSeed()));
    }
    
    public int nextInt() {
        return mix32(this.nextSeed());
    }
    
    public int nextInt(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        final int mix32 = mix32(this.nextSeed());
        final int n2 = n - 1;
        int n3;
        if ((n & n2) == 0x0) {
            n3 = (mix32 & n2);
        }
        else {
            for (int n4 = mix32 >>> 1; n4 + n2 - (n3 = n4 % n) < 0; n4 = mix32(this.nextSeed()) >>> 1) {}
        }
        return n3;
    }
    
    public int nextInt(final int n, final int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return this.internalNextInt(n, n2);
    }
    
    public long nextLong() {
        return mix64(this.nextSeed());
    }
    
    public long nextLong(final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("bound must be positive");
        }
        final long mix64 = mix64(this.nextSeed());
        final long n2 = n - 1L;
        long n3;
        if ((n & n2) == 0x0L) {
            n3 = (mix64 & n2);
        }
        else {
            for (long n4 = mix64 >>> 1; n4 + n2 - (n3 = n4 % n) < 0L; n4 = mix64(this.nextSeed()) >>> 1) {}
        }
        return n3;
    }
    
    public long nextLong(final long n, final long n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return this.internalNextLong(n, n2);
    }
    
    public double nextDouble() {
        return (mix64(this.nextSeed()) >>> 11) * 1.1102230246251565E-16;
    }
    
    public double nextDouble(final double n) {
        if (n <= 0.0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        final double n2 = (mix64(this.nextSeed()) >>> 11) * 1.1102230246251565E-16 * n;
        return (n2 < n) ? n2 : Double.longBitsToDouble(Double.doubleToLongBits(n) - 1L);
    }
    
    public double nextDouble(final double n, final double n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return this.internalNextDouble(n, n2);
    }
    
    public boolean nextBoolean() {
        return mix32(this.nextSeed()) < 0;
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
    
    static {
        defaultGen = new AtomicLong(initialSeed());
    }
    
    static final class RandomDoublesSpliterator implements Spliterator.OfDouble
    {
        final SplittableRandom rng;
        long index;
        final long fence;
        final double origin;
        final double bound;
        
        RandomDoublesSpliterator(final SplittableRandom rng, final long index, final long fence, final double origin, final double bound) {
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
            return (index2 <= index) ? null : new RandomDoublesSpliterator(this.rng.split(), index, this.index = index2, this.origin, this.bound);
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
                final SplittableRandom rng = this.rng;
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
        final SplittableRandom rng;
        long index;
        final long fence;
        final int origin;
        final int bound;
        
        RandomIntsSpliterator(final SplittableRandom rng, final long index, final long fence, final int origin, final int bound) {
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
            return (index2 <= index) ? null : new RandomIntsSpliterator(this.rng.split(), index, this.index = index2, this.origin, this.bound);
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
                final SplittableRandom rng = this.rng;
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
        final SplittableRandom rng;
        long index;
        final long fence;
        final long origin;
        final long bound;
        
        RandomLongsSpliterator(final SplittableRandom rng, final long index, final long fence, final long origin, final long bound) {
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
            return (index2 <= index) ? null : new RandomLongsSpliterator(this.rng.split(), index, this.index = index2, this.origin, this.bound);
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
                final SplittableRandom rng = this.rng;
                final long origin = this.origin;
                final long bound = this.bound;
                do {
                    longConsumer.accept(rng.internalNextLong(origin, bound));
                } while (++index < fence);
            }
        }
    }
}
