package java.nio.channels;

import java.util.concurrent.atomic.*;

public abstract class SelectionKey
{
    public static final int OP_READ = 1;
    public static final int OP_WRITE = 4;
    public static final int OP_CONNECT = 8;
    public static final int OP_ACCEPT = 16;
    private volatile Object attachment;
    private static final AtomicReferenceFieldUpdater<SelectionKey, Object> attachmentUpdater;
    
    protected SelectionKey() {
        this.attachment = null;
    }
    
    public abstract SelectableChannel channel();
    
    public abstract Selector selector();
    
    public abstract boolean isValid();
    
    public abstract void cancel();
    
    public abstract int interestOps();
    
    public abstract SelectionKey interestOps(final int p0);
    
    public abstract int readyOps();
    
    public final boolean isReadable() {
        return (this.readyOps() & 0x1) != 0x0;
    }
    
    public final boolean isWritable() {
        return (this.readyOps() & 0x4) != 0x0;
    }
    
    public final boolean isConnectable() {
        return (this.readyOps() & 0x8) != 0x0;
    }
    
    public final boolean isAcceptable() {
        return (this.readyOps() & 0x10) != 0x0;
    }
    
    public final Object attach(final Object o) {
        return SelectionKey.attachmentUpdater.getAndSet(this, o);
    }
    
    public final Object attachment() {
        return this.attachment;
    }
    
    static {
        attachmentUpdater = AtomicReferenceFieldUpdater.newUpdater(SelectionKey.class, Object.class, "attachment");
    }
}
