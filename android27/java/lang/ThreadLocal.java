package java.lang;

import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.*;
import java.lang.ref.*;

public class ThreadLocal<T>
{
    private final int threadLocalHashCode;
    private static AtomicInteger nextHashCode;
    private static final int HASH_INCREMENT = 1640531527;
    
    private static int nextHashCode() {
        return ThreadLocal.nextHashCode.getAndAdd(1640531527);
    }
    
    protected T initialValue() {
        return null;
    }
    
    public static <S> ThreadLocal<S> withInitial(final Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<S>(supplier);
    }
    
    public ThreadLocal() {
        this.threadLocalHashCode = nextHashCode();
    }
    
    public T get() {
        final ThreadLocalMap map = this.getMap(Thread.currentThread());
        if (map != null) {
            final ThreadLocalMap.Entry access$000 = map.getEntry(this);
            if (access$000 != null) {
                return (T)access$000.value;
            }
        }
        return this.setInitialValue();
    }
    
    private T setInitialValue() {
        final T initialValue = this.initialValue();
        final Thread currentThread = Thread.currentThread();
        final ThreadLocalMap map = this.getMap(currentThread);
        if (map != null) {
            map.set(this, initialValue);
        }
        else {
            this.createMap(currentThread, initialValue);
        }
        return initialValue;
    }
    
    public void set(final T t) {
        final Thread currentThread = Thread.currentThread();
        final ThreadLocalMap map = this.getMap(currentThread);
        if (map != null) {
            map.set(this, t);
        }
        else {
            this.createMap(currentThread, t);
        }
    }
    
    public void remove() {
        final ThreadLocalMap map = this.getMap(Thread.currentThread());
        if (map != null) {
            map.remove(this);
        }
    }
    
    ThreadLocalMap getMap(final Thread thread) {
        return thread.threadLocals;
    }
    
    void createMap(final Thread thread, final T t) {
        thread.threadLocals = new ThreadLocalMap(this, t);
    }
    
    static ThreadLocalMap createInheritedMap(final ThreadLocalMap threadLocalMap) {
        return new ThreadLocalMap(threadLocalMap);
    }
    
    T childValue(final T t) {
        throw new UnsupportedOperationException();
    }
    
    static {
        ThreadLocal.nextHashCode = new AtomicInteger();
    }
    
    static final class SuppliedThreadLocal<T> extends ThreadLocal<T>
    {
        private final Supplier<? extends T> supplier;
        
        SuppliedThreadLocal(final Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }
        
        @Override
        protected T initialValue() {
            return (T)this.supplier.get();
        }
    }
    
    static class ThreadLocalMap
    {
        private static final int INITIAL_CAPACITY = 16;
        private Entry[] table;
        private int size;
        private int threshold;
        
        private void setThreshold(final int n) {
            this.threshold = n * 2 / 3;
        }
        
        private static int nextIndex(final int n, final int n2) {
            return (n + 1 < n2) ? (n + 1) : 0;
        }
        
        private static int prevIndex(final int n, final int n2) {
            return (n - 1 >= 0) ? (n - 1) : (n2 - 1);
        }
        
        ThreadLocalMap(final ThreadLocal<?> threadLocal, final Object o) {
            this.size = 0;
            (this.table = new Entry[16])[threadLocal.threadLocalHashCode & 0xF] = new Entry(threadLocal, o);
            this.size = 1;
            this.setThreshold(16);
        }
        
        private ThreadLocalMap(final ThreadLocalMap threadLocalMap) {
            this.size = 0;
            final Entry[] table = threadLocalMap.table;
            final int length = table.length;
            this.setThreshold(length);
            this.table = new Entry[length];
            for (final Entry entry : table) {
                if (entry != null) {
                    final ThreadLocal<Object> threadLocal = ((Reference<ThreadLocal<Object>>)entry).get();
                    if (threadLocal != null) {
                        final Entry entry2 = new Entry(threadLocal, threadLocal.childValue(entry.value));
                        int nextIndex;
                        for (nextIndex = (threadLocal.threadLocalHashCode & length - 1); this.table[nextIndex] != null; nextIndex = nextIndex(nextIndex, length)) {}
                        this.table[nextIndex] = entry2;
                        ++this.size;
                    }
                }
            }
        }
        
        private Entry getEntry(final ThreadLocal<?> threadLocal) {
            final int n = ((ThreadLocal<Object>)threadLocal).threadLocalHashCode & this.table.length - 1;
            final Entry entry = this.table[n];
            if (entry != null && entry.get() == threadLocal) {
                return entry;
            }
            return this.getEntryAfterMiss(threadLocal, n, entry);
        }
        
        private Entry getEntryAfterMiss(final ThreadLocal<?> threadLocal, int nextIndex, Entry entry) {
            final Entry[] table = this.table;
            final int length = table.length;
            while (entry != null) {
                final ThreadLocal<?> threadLocal2 = entry.get();
                if (threadLocal2 == threadLocal) {
                    return entry;
                }
                if (threadLocal2 == null) {
                    this.expungeStaleEntry(nextIndex);
                }
                else {
                    nextIndex = nextIndex(nextIndex, length);
                }
                entry = table[nextIndex];
            }
            return null;
        }
        
        private void set(final ThreadLocal<?> threadLocal, final Object value) {
            final Entry[] table = this.table;
            final int length = table.length;
            int nextIndex = ((ThreadLocal<Object>)threadLocal).threadLocalHashCode & length - 1;
            for (Entry entry = table[nextIndex]; entry != null; entry = table[nextIndex = nextIndex(nextIndex, length)]) {
                final ThreadLocal<Object> threadLocal2 = ((Reference<ThreadLocal<Object>>)entry).get();
                if (threadLocal2 == threadLocal) {
                    entry.value = value;
                    return;
                }
                if (threadLocal2 == null) {
                    this.replaceStaleEntry(threadLocal, value, nextIndex);
                    return;
                }
            }
            table[nextIndex] = new Entry(threadLocal, value);
            final int n = ++this.size;
            if (!this.cleanSomeSlots(nextIndex, n) && n >= this.threshold) {
                this.rehash();
            }
        }
        
        private void remove(final ThreadLocal<?> threadLocal) {
            final Entry[] table = this.table;
            final int length = table.length;
            int nextIndex = ((ThreadLocal<Object>)threadLocal).threadLocalHashCode & length - 1;
            for (Entry entry = table[nextIndex]; entry != null; entry = table[nextIndex = nextIndex(nextIndex, length)]) {
                if (entry.get() == threadLocal) {
                    entry.clear();
                    this.expungeStaleEntry(nextIndex);
                    return;
                }
            }
        }
        
        private void replaceStaleEntry(final ThreadLocal<?> threadLocal, final Object value, final int n) {
            final Entry[] table = this.table;
            final int length = table.length;
            int n2 = n;
            Entry entry;
            for (int n3 = prevIndex(n, length); (entry = table[n3]) != null; n3 = prevIndex(n3, length)) {
                if (entry.get() == null) {
                    n2 = n3;
                }
            }
            Entry entry2;
            for (int n4 = nextIndex(n, length); (entry2 = table[n4]) != null; n4 = nextIndex(n4, length)) {
                final ThreadLocal<?> threadLocal2 = entry2.get();
                if (threadLocal2 == threadLocal) {
                    entry2.value = value;
                    table[n4] = table[n];
                    table[n] = entry2;
                    if (n2 == n) {
                        n2 = n4;
                    }
                    this.cleanSomeSlots(this.expungeStaleEntry(n2), length);
                    return;
                }
                if (threadLocal2 == null && n2 == n) {
                    n2 = n4;
                }
            }
            table[n].value = null;
            table[n] = new Entry(threadLocal, value);
            if (n2 != n) {
                this.cleanSomeSlots(this.expungeStaleEntry(n2), length);
            }
        }
        
        private int expungeStaleEntry(final int n) {
            final Entry[] table = this.table;
            final int length = table.length;
            table[n].value = null;
            table[n] = null;
            --this.size;
            int n2;
            Entry entry;
            for (n2 = nextIndex(n, length); (entry = table[n2]) != null; n2 = nextIndex(n2, length)) {
                final ThreadLocal<Object> threadLocal = ((Reference<ThreadLocal<Object>>)entry).get();
                if (threadLocal == null) {
                    entry.value = null;
                    table[n2] = null;
                    --this.size;
                }
                else {
                    int nextIndex = threadLocal.threadLocalHashCode & length - 1;
                    if (nextIndex != n2) {
                        table[n2] = null;
                        while (table[nextIndex] != null) {
                            nextIndex = nextIndex(nextIndex, length);
                        }
                        table[nextIndex] = entry;
                    }
                }
            }
            return n2;
        }
        
        private boolean cleanSomeSlots(int n, int n2) {
            boolean b = false;
            final Entry[] table = this.table;
            final int length = table.length;
            do {
                n = nextIndex(n, length);
                final Entry entry = table[n];
                if (entry != null && entry.get() == null) {
                    n2 = length;
                    b = true;
                    n = this.expungeStaleEntry(n);
                }
            } while ((n2 >>>= 1) != 0);
            return b;
        }
        
        private void rehash() {
            this.expungeStaleEntries();
            if (this.size >= this.threshold - this.threshold / 4) {
                this.resize();
            }
        }
        
        private void resize() {
            final Entry[] table = this.table;
            final int length = table.length;
            final int threshold = length * 2;
            final Entry[] table2 = new Entry[threshold];
            int size = 0;
            for (final Entry entry : table) {
                if (entry != null) {
                    final ThreadLocal<Object> threadLocal = ((Reference<ThreadLocal<Object>>)entry).get();
                    if (threadLocal == null) {
                        entry.value = null;
                    }
                    else {
                        int nextIndex;
                        for (nextIndex = (threadLocal.threadLocalHashCode & threshold - 1); table2[nextIndex] != null; nextIndex = nextIndex(nextIndex, threshold)) {}
                        table2[nextIndex] = entry;
                        ++size;
                    }
                }
            }
            this.setThreshold(threshold);
            this.size = size;
            this.table = table2;
        }
        
        private void expungeStaleEntries() {
            final Entry[] table = this.table;
            for (int length = table.length, i = 0; i < length; ++i) {
                final Entry entry = table[i];
                if (entry != null && entry.get() == null) {
                    this.expungeStaleEntry(i);
                }
            }
        }
        
        static class Entry extends WeakReference<ThreadLocal<?>>
        {
            Object value;
            
            Entry(final ThreadLocal<?> threadLocal, final Object value) {
                super(threadLocal);
                this.value = value;
            }
        }
    }
}
