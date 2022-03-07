package java.nio;

import java.io.*;
import sun.misc.*;

public abstract class MappedByteBuffer extends ByteBuffer
{
    private final FileDescriptor fd;
    private static byte unused;
    
    MappedByteBuffer(final int n, final int n2, final int n3, final int n4, final FileDescriptor fd) {
        super(n, n2, n3, n4);
        this.fd = fd;
    }
    
    MappedByteBuffer(final int n, final int n2, final int n3, final int n4) {
        super(n, n2, n3, n4);
        this.fd = null;
    }
    
    private void checkMapped() {
        if (this.fd == null) {
            throw new UnsupportedOperationException();
        }
    }
    
    private long mappingOffset() {
        final int pageSize = Bits.pageSize();
        final long n = this.address % pageSize;
        return (n >= 0L) ? n : (pageSize + n);
    }
    
    private long mappingAddress(final long n) {
        return this.address - n;
    }
    
    private long mappingLength(final long n) {
        return this.capacity() + n;
    }
    
    public final boolean isLoaded() {
        this.checkMapped();
        if (this.address == 0L || this.capacity() == 0) {
            return true;
        }
        final long mappingOffset = this.mappingOffset();
        final long mappingLength = this.mappingLength(mappingOffset);
        return this.isLoaded0(this.mappingAddress(mappingOffset), mappingLength, Bits.pageCount(mappingLength));
    }
    
    public final MappedByteBuffer load() {
        this.checkMapped();
        if (this.address == 0L || this.capacity() == 0) {
            return this;
        }
        final long mappingOffset = this.mappingOffset();
        final long mappingLength = this.mappingLength(mappingOffset);
        this.load0(this.mappingAddress(mappingOffset), mappingLength);
        final Unsafe unsafe = Unsafe.getUnsafe();
        final int pageSize = Bits.pageSize();
        final int pageCount = Bits.pageCount(mappingLength);
        long mappingAddress = this.mappingAddress(mappingOffset);
        byte unused = 0;
        for (int i = 0; i < pageCount; ++i) {
            unused ^= unsafe.getByte(mappingAddress);
            mappingAddress += pageSize;
        }
        if (MappedByteBuffer.unused != 0) {
            MappedByteBuffer.unused = unused;
        }
        return this;
    }
    
    public final MappedByteBuffer force() {
        this.checkMapped();
        if (this.address != 0L && this.capacity() != 0) {
            final long mappingOffset = this.mappingOffset();
            this.force0(this.fd, this.mappingAddress(mappingOffset), this.mappingLength(mappingOffset));
        }
        return this;
    }
    
    private native boolean isLoaded0(final long p0, final long p1, final int p2);
    
    private native void load0(final long p0, final long p1);
    
    private native void force0(final FileDescriptor p0, final long p1, final long p2);
}
