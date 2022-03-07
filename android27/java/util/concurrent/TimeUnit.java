package java.util.concurrent;

public enum TimeUnit
{
    NANOSECONDS {
        @Override
        public long toNanos(final long n) {
            return n;
        }
        
        @Override
        public long toMicros(final long n) {
            return n / 1000L;
        }
        
        @Override
        public long toMillis(final long n) {
            return n / 1000000L;
        }
        
        @Override
        public long toSeconds(final long n) {
            return n / 1000000000L;
        }
        
        @Override
        public long toMinutes(final long n) {
            return n / 60000000000L;
        }
        
        @Override
        public long toHours(final long n) {
            return n / 3600000000000L;
        }
        
        @Override
        public long toDays(final long n) {
            return n / 86400000000000L;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toNanos(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return (int)(n - n2 * 1000000L);
        }
    }, 
    MICROSECONDS {
        @Override
        public long toNanos(final long n) {
            return TimeUnit.x(n, 1000L, 9223372036854775L);
        }
        
        @Override
        public long toMicros(final long n) {
            return n;
        }
        
        @Override
        public long toMillis(final long n) {
            return n / 1000L;
        }
        
        @Override
        public long toSeconds(final long n) {
            return n / 1000000L;
        }
        
        @Override
        public long toMinutes(final long n) {
            return n / 60000000L;
        }
        
        @Override
        public long toHours(final long n) {
            return n / 3600000000L;
        }
        
        @Override
        public long toDays(final long n) {
            return n / 86400000000L;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toMicros(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return (int)(n * 1000L - n2 * 1000000L);
        }
    }, 
    MILLISECONDS {
        @Override
        public long toNanos(final long n) {
            return TimeUnit.x(n, 1000000L, 9223372036854L);
        }
        
        @Override
        public long toMicros(final long n) {
            return TimeUnit.x(n, 1000L, 9223372036854775L);
        }
        
        @Override
        public long toMillis(final long n) {
            return n;
        }
        
        @Override
        public long toSeconds(final long n) {
            return n / 1000L;
        }
        
        @Override
        public long toMinutes(final long n) {
            return n / 60000L;
        }
        
        @Override
        public long toHours(final long n) {
            return n / 3600000L;
        }
        
        @Override
        public long toDays(final long n) {
            return n / 86400000L;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toMillis(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return 0;
        }
    }, 
    SECONDS {
        @Override
        public long toNanos(final long n) {
            return TimeUnit.x(n, 1000000000L, 9223372036L);
        }
        
        @Override
        public long toMicros(final long n) {
            return TimeUnit.x(n, 1000000L, 9223372036854L);
        }
        
        @Override
        public long toMillis(final long n) {
            return TimeUnit.x(n, 1000L, 9223372036854775L);
        }
        
        @Override
        public long toSeconds(final long n) {
            return n;
        }
        
        @Override
        public long toMinutes(final long n) {
            return n / 60L;
        }
        
        @Override
        public long toHours(final long n) {
            return n / 3600L;
        }
        
        @Override
        public long toDays(final long n) {
            return n / 86400L;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toSeconds(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return 0;
        }
    }, 
    MINUTES {
        @Override
        public long toNanos(final long n) {
            return TimeUnit.x(n, 60000000000L, 153722867L);
        }
        
        @Override
        public long toMicros(final long n) {
            return TimeUnit.x(n, 60000000L, 153722867280L);
        }
        
        @Override
        public long toMillis(final long n) {
            return TimeUnit.x(n, 60000L, 153722867280912L);
        }
        
        @Override
        public long toSeconds(final long n) {
            return TimeUnit.x(n, 60L, 153722867280912930L);
        }
        
        @Override
        public long toMinutes(final long n) {
            return n;
        }
        
        @Override
        public long toHours(final long n) {
            return n / 60L;
        }
        
        @Override
        public long toDays(final long n) {
            return n / 1440L;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toMinutes(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return 0;
        }
    }, 
    HOURS {
        @Override
        public long toNanos(final long n) {
            return TimeUnit.x(n, 3600000000000L, 2562047L);
        }
        
        @Override
        public long toMicros(final long n) {
            return TimeUnit.x(n, 3600000000L, 2562047788L);
        }
        
        @Override
        public long toMillis(final long n) {
            return TimeUnit.x(n, 3600000L, 2562047788015L);
        }
        
        @Override
        public long toSeconds(final long n) {
            return TimeUnit.x(n, 3600L, 2562047788015215L);
        }
        
        @Override
        public long toMinutes(final long n) {
            return TimeUnit.x(n, 60L, 153722867280912930L);
        }
        
        @Override
        public long toHours(final long n) {
            return n;
        }
        
        @Override
        public long toDays(final long n) {
            return n / 24L;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toHours(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return 0;
        }
    }, 
    DAYS {
        @Override
        public long toNanos(final long n) {
            return TimeUnit.x(n, 86400000000000L, 106751L);
        }
        
        @Override
        public long toMicros(final long n) {
            return TimeUnit.x(n, 86400000000L, 106751991L);
        }
        
        @Override
        public long toMillis(final long n) {
            return TimeUnit.x(n, 86400000L, 106751991167L);
        }
        
        @Override
        public long toSeconds(final long n) {
            return TimeUnit.x(n, 86400L, 106751991167300L);
        }
        
        @Override
        public long toMinutes(final long n) {
            return TimeUnit.x(n, 1440L, 6405119470038038L);
        }
        
        @Override
        public long toHours(final long n) {
            return TimeUnit.x(n, 24L, 384307168202282325L);
        }
        
        @Override
        public long toDays(final long n) {
            return n;
        }
        
        @Override
        public long convert(final long n, final TimeUnit timeUnit) {
            return timeUnit.toDays(n);
        }
        
        @Override
        int excessNanos(final long n, final long n2) {
            return 0;
        }
    };
    
    static final long C0 = 1L;
    static final long C1 = 1000L;
    static final long C2 = 1000000L;
    static final long C3 = 1000000000L;
    static final long C4 = 60000000000L;
    static final long C5 = 3600000000000L;
    static final long C6 = 86400000000000L;
    static final long MAX = Long.MAX_VALUE;
    
    static long x(final long n, final long n2, final long n3) {
        if (n > n3) {
            return Long.MAX_VALUE;
        }
        if (n < -n3) {
            return Long.MIN_VALUE;
        }
        return n * n2;
    }
    
    public long convert(final long n, final TimeUnit timeUnit) {
        throw new AbstractMethodError();
    }
    
    public long toNanos(final long n) {
        throw new AbstractMethodError();
    }
    
    public long toMicros(final long n) {
        throw new AbstractMethodError();
    }
    
    public long toMillis(final long n) {
        throw new AbstractMethodError();
    }
    
    public long toSeconds(final long n) {
        throw new AbstractMethodError();
    }
    
    public long toMinutes(final long n) {
        throw new AbstractMethodError();
    }
    
    public long toHours(final long n) {
        throw new AbstractMethodError();
    }
    
    public long toDays(final long n) {
        throw new AbstractMethodError();
    }
    
    abstract int excessNanos(final long p0, final long p1);
    
    public void timedWait(final Object o, final long n) throws InterruptedException {
        if (n > 0L) {
            final long millis = this.toMillis(n);
            o.wait(millis, this.excessNanos(n, millis));
        }
    }
    
    public void timedJoin(final Thread thread, final long n) throws InterruptedException {
        if (n > 0L) {
            final long millis = this.toMillis(n);
            thread.join(millis, this.excessNanos(n, millis));
        }
    }
    
    public void sleep(final long n) throws InterruptedException {
        if (n > 0L) {
            final long millis = this.toMillis(n);
            Thread.sleep(millis, this.excessNanos(n, millis));
        }
    }
}
