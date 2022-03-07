package java.nio.channels.spi;

import sun.nio.ch.*;
import java.io.*;
import java.nio.channels.*;
import sun.misc.*;

public abstract class AbstractInterruptibleChannel implements Channel, InterruptibleChannel
{
    private final Object closeLock;
    private volatile boolean open;
    private Interruptible interruptor;
    private volatile Thread interrupted;
    
    protected AbstractInterruptibleChannel() {
        this.closeLock = new Object();
        this.open = true;
    }
    
    @Override
    public final void close() throws IOException {
        synchronized (this.closeLock) {
            if (!this.open) {
                return;
            }
            this.open = false;
            this.implCloseChannel();
        }
    }
    
    protected abstract void implCloseChannel() throws IOException;
    
    @Override
    public final boolean isOpen() {
        return this.open;
    }
    
    protected final void begin() {
        if (this.interruptor == null) {
            this.interruptor = new Interruptible() {
                @Override
                public void interrupt(final Thread thread) {
                    synchronized (AbstractInterruptibleChannel.this.closeLock) {
                        if (!AbstractInterruptibleChannel.this.open) {
                            return;
                        }
                        AbstractInterruptibleChannel.this.open = false;
                        AbstractInterruptibleChannel.this.interrupted = thread;
                        try {
                            AbstractInterruptibleChannel.this.implCloseChannel();
                        }
                        catch (IOException ex) {}
                    }
                }
            };
        }
        blockedOn(this.interruptor);
        final Thread currentThread = Thread.currentThread();
        if (currentThread.isInterrupted()) {
            this.interruptor.interrupt(currentThread);
        }
    }
    
    protected final void end(final boolean b) throws AsynchronousCloseException {
        blockedOn(null);
        final Thread interrupted = this.interrupted;
        if (interrupted != null && interrupted == Thread.currentThread()) {
            throw new ClosedByInterruptException();
        }
        if (!b && !this.open) {
            throw new AsynchronousCloseException();
        }
    }
    
    static void blockedOn(final Interruptible interruptible) {
        SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), interruptible);
    }
}
