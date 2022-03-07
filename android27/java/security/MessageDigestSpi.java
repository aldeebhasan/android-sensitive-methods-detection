package java.security;

import java.nio.*;
import sun.security.jca.*;

public abstract class MessageDigestSpi
{
    private byte[] tempArray;
    
    protected int engineGetDigestLength() {
        return 0;
    }
    
    protected abstract void engineUpdate(final byte p0);
    
    protected abstract void engineUpdate(final byte[] p0, final int p1, final int p2);
    
    protected void engineUpdate(final ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return;
        }
        if (byteBuffer.hasArray()) {
            final byte[] array = byteBuffer.array();
            final int arrayOffset = byteBuffer.arrayOffset();
            final int position = byteBuffer.position();
            final int limit = byteBuffer.limit();
            this.engineUpdate(array, arrayOffset + position, limit - position);
            byteBuffer.position(limit);
        }
        else {
            int i = byteBuffer.remaining();
            final int tempArraySize = JCAUtil.getTempArraySize(i);
            if (this.tempArray == null || tempArraySize > this.tempArray.length) {
                this.tempArray = new byte[tempArraySize];
            }
            while (i > 0) {
                final int min = Math.min(i, this.tempArray.length);
                byteBuffer.get(this.tempArray, 0, min);
                this.engineUpdate(this.tempArray, 0, min);
                i -= min;
            }
        }
    }
    
    protected abstract byte[] engineDigest();
    
    protected int engineDigest(final byte[] array, final int n, final int n2) throws DigestException {
        final byte[] engineDigest = this.engineDigest();
        if (n2 < engineDigest.length) {
            throw new DigestException("partial digests not returned");
        }
        if (array.length - n < engineDigest.length) {
            throw new DigestException("insufficient space in the output buffer to store the digest");
        }
        System.arraycopy(engineDigest, 0, array, n, engineDigest.length);
        return engineDigest.length;
    }
    
    protected abstract void engineReset();
    
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }
}
