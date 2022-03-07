package java.nio.channels;

import java.nio.file.attribute.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.*;

public abstract class AsynchronousFileChannel implements AsynchronousChannel
{
    private static final FileAttribute<?>[] NO_ATTRIBUTES;
    
    public static AsynchronousFileChannel open(final Path path, final Set<? extends OpenOption> set, final ExecutorService executorService, final FileAttribute<?>... array) throws IOException {
        return path.getFileSystem().provider().newAsynchronousFileChannel(path, set, executorService, array);
    }
    
    public static AsynchronousFileChannel open(final Path path, final OpenOption... array) throws IOException {
        final HashSet<Object> set = new HashSet<Object>(array.length);
        Collections.addAll(set, array);
        return open(path, (Set<? extends OpenOption>)set, null, AsynchronousFileChannel.NO_ATTRIBUTES);
    }
    
    public abstract long size() throws IOException;
    
    public abstract AsynchronousFileChannel truncate(final long p0) throws IOException;
    
    public abstract void force(final boolean p0) throws IOException;
    
    public abstract <A> void lock(final long p0, final long p1, final boolean p2, final A p3, final CompletionHandler<FileLock, ? super A> p4);
    
    public final <A> void lock(final A a, final CompletionHandler<FileLock, ? super A> completionHandler) {
        this.lock(0L, Long.MAX_VALUE, false, a, completionHandler);
    }
    
    public abstract Future<FileLock> lock(final long p0, final long p1, final boolean p2);
    
    public final Future<FileLock> lock() {
        return this.lock(0L, Long.MAX_VALUE, false);
    }
    
    public abstract FileLock tryLock(final long p0, final long p1, final boolean p2) throws IOException;
    
    public final FileLock tryLock() throws IOException {
        return this.tryLock(0L, Long.MAX_VALUE, false);
    }
    
    public abstract <A> void read(final ByteBuffer p0, final long p1, final A p2, final CompletionHandler<Integer, ? super A> p3);
    
    public abstract Future<Integer> read(final ByteBuffer p0, final long p1);
    
    public abstract <A> void write(final ByteBuffer p0, final long p1, final A p2, final CompletionHandler<Integer, ? super A> p3);
    
    public abstract Future<Integer> write(final ByteBuffer p0, final long p1);
    
    static {
        NO_ATTRIBUTES = new FileAttribute[0];
    }
}
