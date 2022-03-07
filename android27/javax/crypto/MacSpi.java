package javax.crypto;

import java.security.spec.*;
import java.security.*;
import java.nio.*;

public abstract class MacSpi
{
    protected abstract int engineGetMacLength();
    
    protected abstract void engineInit(final Key p0, final AlgorithmParameterSpec p1) throws InvalidKeyException, InvalidAlgorithmParameterException;
    
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
            final byte[] array2 = new byte[CipherSpi.getTempArraySize(i)];
            while (i > 0) {
                final int min = Math.min(i, array2.length);
                byteBuffer.get(array2, 0, min);
                this.engineUpdate(array2, 0, min);
                i -= min;
            }
        }
    }
    
    protected abstract byte[] engineDoFinal();
    
    protected abstract void engineReset();
    
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }
}
