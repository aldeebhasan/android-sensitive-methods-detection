package java.nio.channels;

import java.io.*;

public abstract class FileLock implements AutoCloseable
{
    private final Channel channel;
    private final long position;
    private final long size;
    private final boolean shared;
    
    protected FileLock(final FileChannel channel, final long position, final long size, final boolean shared) {
        if (position < 0L) {
            throw new IllegalArgumentException("Negative position");
        }
        if (size < 0L) {
            throw new IllegalArgumentException("Negative size");
        }
        if (position + size < 0L) {
            throw new IllegalArgumentException("Negative position + size");
        }
        this.channel = channel;
        this.position = position;
        this.size = size;
        this.shared = shared;
    }
    
    protected FileLock(final AsynchronousFileChannel channel, final long position, final long size, final boolean shared) {
        if (position < 0L) {
            throw new IllegalArgumentException("Negative position");
        }
        if (size < 0L) {
            throw new IllegalArgumentException("Negative size");
        }
        if (position + size < 0L) {
            throw new IllegalArgumentException("Negative position + size");
        }
        this.channel = channel;
        this.position = position;
        this.size = size;
        this.shared = shared;
    }
    
    public final FileChannel channel() {
        return (this.channel instanceof FileChannel) ? ((FileChannel)this.channel) : null;
    }
    
    public Channel acquiredBy() {
        return this.channel;
    }
    
    public final long position() {
        return this.position;
    }
    
    public final long size() {
        return this.size;
    }
    
    public final boolean isShared() {
        return this.shared;
    }
    
    public final boolean overlaps(final long n, final long n2) {
        return n + n2 > this.position && this.position + this.size > n;
    }
    
    public abstract boolean isValid();
    
    public abstract void release() throws IOException;
    
    @Override
    public final void close() throws IOException {
        this.release();
    }
    
    @Override
    public final String toString() {
        return this.getClass().getName() + "[" + this.position + ":" + this.size + " " + (this.shared ? "shared" : "exclusive") + " " + (this.isValid() ? "valid" : "invalid") + "]";
    }
}
