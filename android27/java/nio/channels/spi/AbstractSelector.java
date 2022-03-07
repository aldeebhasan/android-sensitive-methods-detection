package java.nio.channels.spi;

import java.util.concurrent.atomic.*;
import java.nio.channels.*;
import sun.nio.ch.*;
import java.util.*;
import java.io.*;

public abstract class AbstractSelector extends Selector
{
    private AtomicBoolean selectorOpen;
    private final SelectorProvider provider;
    private final Set<SelectionKey> cancelledKeys;
    private Interruptible interruptor;
    
    protected AbstractSelector(final SelectorProvider provider) {
        this.selectorOpen = new AtomicBoolean(true);
        this.cancelledKeys = new HashSet<SelectionKey>();
        this.interruptor = null;
        this.provider = provider;
    }
    
    void cancel(final SelectionKey selectionKey) {
        synchronized (this.cancelledKeys) {
            this.cancelledKeys.add(selectionKey);
        }
    }
    
    @Override
    public final void close() throws IOException {
        if (!this.selectorOpen.getAndSet(false)) {
            return;
        }
        this.implCloseSelector();
    }
    
    protected abstract void implCloseSelector() throws IOException;
    
    @Override
    public final boolean isOpen() {
        return this.selectorOpen.get();
    }
    
    @Override
    public final SelectorProvider provider() {
        return this.provider;
    }
    
    protected final Set<SelectionKey> cancelledKeys() {
        return this.cancelledKeys;
    }
    
    protected abstract SelectionKey register(final AbstractSelectableChannel p0, final int p1, final Object p2);
    
    protected final void deregister(final AbstractSelectionKey abstractSelectionKey) {
        ((AbstractSelectableChannel)abstractSelectionKey.channel()).removeKey(abstractSelectionKey);
    }
    
    protected final void begin() {
        if (this.interruptor == null) {
            this.interruptor = new Interruptible() {
                @Override
                public void interrupt(final Thread thread) {
                    AbstractSelector.this.wakeup();
                }
            };
        }
        AbstractInterruptibleChannel.blockedOn(this.interruptor);
        final Thread currentThread = Thread.currentThread();
        if (currentThread.isInterrupted()) {
            this.interruptor.interrupt(currentThread);
        }
    }
    
    protected final void end() {
        AbstractInterruptibleChannel.blockedOn(null);
    }
}
