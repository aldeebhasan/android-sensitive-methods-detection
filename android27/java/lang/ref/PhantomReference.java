package java.lang.ref;

public class PhantomReference<T> extends Reference<T>
{
    @Override
    public T get() {
        return null;
    }
    
    public PhantomReference(final T t, final ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }
}
