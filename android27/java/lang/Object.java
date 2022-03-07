package java.lang;

public class Object
{
    public Object() {
    }
    
    private static native void registerNatives();
    
    public final native Class<?> getClass();
    
    public native int hashCode();
    
    public boolean equals(final Object o) {
        return this == o;
    }
    
    protected native Object clone() throws CloneNotSupportedException;
    
    public String toString() {
        return this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
    }
    
    public final native void notify();
    
    public final native void notifyAll();
    
    public final native void wait(final long p0) throws InterruptedException;
    
    public final void wait(long n, final int n2) throws InterruptedException {
        if (n < 0L) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (n2 < 0 || n2 > 999999) {
            throw new IllegalArgumentException("nanosecond timeout value out of range");
        }
        if (n2 > 0) {
            ++n;
        }
        this.wait(n);
    }
    
    public final void wait() throws InterruptedException {
        this.wait(0L);
    }
    
    protected void finalize() throws Throwable {
    }
    
    static {
        registerNatives();
    }
}
