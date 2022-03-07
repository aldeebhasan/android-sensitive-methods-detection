package java.lang.ref;

public class WeakReference<T> extends Reference<T>
{
    public WeakReference(final T t) {
        super(t);
    }
    
    public WeakReference(final T t, final ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }
}
