package java.nio.channels;

import java.nio.channels.spi.*;
import java.nio.file.attribute.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.nio.*;

public abstract class FileChannel extends AbstractInterruptibleChannel implements SeekableByteChannel, GatheringByteChannel, ScatteringByteChannel
{
    private static final FileAttribute<?>[] NO_ATTRIBUTES;
    
    public static FileChannel open(final Path path, final Set<? extends OpenOption> set, final FileAttribute<?>... array) throws IOException {
        return path.getFileSystem().provider().newFileChannel(path, set, array);
    }
    
    public static FileChannel open(final Path path, final OpenOption... array) throws IOException {
        final HashSet<Object> set = new HashSet<Object>(array.length);
        Collections.addAll(set, array);
        return open(path, (Set<? extends OpenOption>)set, FileChannel.NO_ATTRIBUTES);
    }
    
    @Override
    public abstract int read(final ByteBuffer p0) throws IOException;
    
    @Override
    public abstract long read(final ByteBuffer[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public final long read(final ByteBuffer[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public abstract int write(final ByteBuffer p0) throws IOException;
    
    @Override
    public abstract long write(final ByteBuffer[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public final long write(final ByteBuffer[] array) throws IOException {
        return this.write(array, 0, array.length);
    }
    
    @Override
    public abstract long position() throws IOException;
    
    @Override
    public abstract FileChannel position(final long p0) throws IOException;
    
    @Override
    public abstract long size() throws IOException;
    
    @Override
    public abstract FileChannel truncate(final long p0) throws IOException;
    
    public abstract void force(final boolean p0) throws IOException;
    
    public abstract long transferTo(final long p0, final long p1, final WritableByteChannel p2) throws IOException;
    
    public abstract long transferFrom(final ReadableByteChannel p0, final long p1, final long p2) throws IOException;
    
    public abstract int read(final ByteBuffer p0, final long p1) throws IOException;
    
    public abstract int write(final ByteBuffer p0, final long p1) throws IOException;
    
    public abstract MappedByteBuffer map(final MapMode p0, final long p1, final long p2) throws IOException;
    
    public abstract FileLock lock(final long p0, final long p1, final boolean p2) throws IOException;
    
    public final FileLock lock() throws IOException {
        return this.lock(0L, Long.MAX_VALUE, false);
    }
    
    public abstract FileLock tryLock(final long p0, final long p1, final boolean p2) throws IOException;
    
    public final FileLock tryLock() throws IOException {
        return this.tryLock(0L, Long.MAX_VALUE, false);
    }
    
    static {
        NO_ATTRIBUTES = new FileAttribute[0];
    }
    
    public static class MapMode
    {
        public static final MapMode READ_ONLY;
        public static final MapMode READ_WRITE;
        public static final MapMode PRIVATE;
        private final String name;
        
        private MapMode(final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            READ_ONLY = new MapMode("READ_ONLY");
            READ_WRITE = new MapMode("READ_WRITE");
            PRIVATE = new MapMode("PRIVATE");
        }
    }
}
