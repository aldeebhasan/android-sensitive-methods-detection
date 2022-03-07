package java.lang;

public class InheritableThreadLocal<T> extends ThreadLocal<T>
{
    protected T childValue(final T t) {
        return t;
    }
    
    @Override
    ThreadLocalMap getMap(final Thread thread) {
        return thread.inheritableThreadLocals;
    }
    
    @Override
    void createMap(final Thread thread, final T t) {
        thread.inheritableThreadLocals = new ThreadLocalMap(this, t);
    }
}
