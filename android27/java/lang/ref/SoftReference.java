package java.lang.ref;

public class SoftReference<T> extends Reference<T>
{
    private static long clock;
    private long timestamp;
    
    public SoftReference(final T t) {
        super(t);
        this.timestamp = SoftReference.clock;
    }
    
    public SoftReference(final T t, final ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
        this.timestamp = SoftReference.clock;
    }
    
    @Override
    public T get() {
        final T value = super.get();
        if (value != null && this.timestamp != SoftReference.clock) {
            this.timestamp = SoftReference.clock;
        }
        return value;
    }
}
