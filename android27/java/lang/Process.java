package java.lang;

import java.io.*;
import java.util.concurrent.*;

public abstract class Process
{
    public abstract OutputStream getOutputStream();
    
    public abstract InputStream getInputStream();
    
    public abstract InputStream getErrorStream();
    
    public abstract int waitFor() throws InterruptedException;
    
    public boolean waitFor(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final long nanoTime = System.nanoTime();
        long nanos = timeUnit.toNanos(n);
        while (true) {
            try {
                this.exitValue();
                return true;
            }
            catch (IllegalThreadStateException ex) {
                if (nanos > 0L) {
                    Thread.sleep(Math.min(TimeUnit.NANOSECONDS.toMillis(nanos) + 1L, 100L));
                }
                nanos = timeUnit.toNanos(n) - (System.nanoTime() - nanoTime);
                if (nanos <= 0L) {
                    return false;
                }
                continue;
            }
            break;
        }
    }
    
    public abstract int exitValue();
    
    public abstract void destroy();
    
    public Process destroyForcibly() {
        this.destroy();
        return this;
    }
    
    public boolean isAlive() {
        try {
            this.exitValue();
            return false;
        }
        catch (IllegalThreadStateException ex) {
            return true;
        }
    }
}
