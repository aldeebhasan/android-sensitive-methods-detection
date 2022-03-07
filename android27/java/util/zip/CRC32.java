package java.util.zip;

import java.nio.*;
import sun.nio.ch.*;

public class CRC32 implements Checksum
{
    private int crc;
    
    @Override
    public void update(final int n) {
        this.crc = update(this.crc, n);
    }
    
    @Override
    public void update(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.crc = updateBytes(this.crc, array, n, n2);
    }
    
    public void update(final byte[] array) {
        this.crc = updateBytes(this.crc, array, 0, array.length);
    }
    
    public void update(final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        final int limit = byteBuffer.limit();
        assert position <= limit;
        final int n = limit - position;
        if (n <= 0) {
            return;
        }
        if (byteBuffer instanceof DirectBuffer) {
            this.crc = updateByteBuffer(this.crc, ((DirectBuffer)byteBuffer).address(), position, n);
        }
        else if (byteBuffer.hasArray()) {
            this.crc = updateBytes(this.crc, byteBuffer.array(), position + byteBuffer.arrayOffset(), n);
        }
        else {
            final byte[] array = new byte[n];
            byteBuffer.get(array);
            this.crc = updateBytes(this.crc, array, 0, array.length);
        }
        byteBuffer.position(limit);
    }
    
    @Override
    public void reset() {
        this.crc = 0;
    }
    
    @Override
    public long getValue() {
        return this.crc & 0xFFFFFFFFL;
    }
    
    private static native int update(final int p0, final int p1);
    
    private static native int updateBytes(final int p0, final byte[] p1, final int p2, final int p3);
    
    private static native int updateByteBuffer(final int p0, final long p1, final int p2, final int p3);
}
