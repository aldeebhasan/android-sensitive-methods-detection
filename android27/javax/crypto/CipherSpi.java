package javax.crypto;

import java.security.spec.*;
import java.nio.*;
import java.security.*;

public abstract class CipherSpi
{
    protected abstract void engineSetMode(final String p0) throws NoSuchAlgorithmException;
    
    protected abstract void engineSetPadding(final String p0) throws NoSuchPaddingException;
    
    protected abstract int engineGetBlockSize();
    
    protected abstract int engineGetOutputSize(final int p0);
    
    protected abstract byte[] engineGetIV();
    
    protected abstract AlgorithmParameters engineGetParameters();
    
    protected abstract void engineInit(final int p0, final Key p1, final SecureRandom p2) throws InvalidKeyException;
    
    protected abstract void engineInit(final int p0, final Key p1, final AlgorithmParameterSpec p2, final SecureRandom p3) throws InvalidKeyException, InvalidAlgorithmParameterException;
    
    protected abstract void engineInit(final int p0, final Key p1, final AlgorithmParameters p2, final SecureRandom p3) throws InvalidKeyException, InvalidAlgorithmParameterException;
    
    protected abstract byte[] engineUpdate(final byte[] p0, final int p1, final int p2);
    
    protected abstract int engineUpdate(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws ShortBufferException;
    
    protected int engineUpdate(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws ShortBufferException {
        try {
            return this.bufferCrypt(byteBuffer, byteBuffer2, true);
        }
        catch (IllegalBlockSizeException ex) {
            throw new ProviderException("Internal error in update()");
        }
        catch (BadPaddingException ex2) {
            throw new ProviderException("Internal error in update()");
        }
    }
    
    protected abstract byte[] engineDoFinal(final byte[] p0, final int p1, final int p2) throws IllegalBlockSizeException, BadPaddingException;
    
    protected abstract int engineDoFinal(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException;
    
    protected int engineDoFinal(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        return this.bufferCrypt(byteBuffer, byteBuffer2, false);
    }
    
    static int getTempArraySize(final int n) {
        return Math.min(4096, n);
    }
    
    private int bufferCrypt(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final boolean b) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        if (byteBuffer == null || byteBuffer2 == null) {
            throw new NullPointerException("Input and output buffers must not be null");
        }
        final int position = byteBuffer.position();
        final int limit = byteBuffer.limit();
        int i = limit - position;
        if (b && i == 0) {
            return 0;
        }
        final int engineGetOutputSize = this.engineGetOutputSize(i);
        if (byteBuffer2.remaining() < engineGetOutputSize) {
            throw new ShortBufferException("Need at least " + engineGetOutputSize + " bytes of space in output buffer");
        }
        final boolean hasArray = byteBuffer.hasArray();
        final boolean hasArray2 = byteBuffer2.hasArray();
        int n = 0;
        if (hasArray2) {
            final byte[] array = byteBuffer2.array();
            final int position2 = byteBuffer2.position();
            int n2 = byteBuffer2.arrayOffset() + position2;
            if (hasArray) {
                final byte[] array2 = byteBuffer.array();
                final int n3 = byteBuffer.arrayOffset() + position;
                if (b) {
                    n = this.engineUpdate(array2, n3, i, array, n2);
                }
                else {
                    n = this.engineDoFinal(array2, n3, i, array, n2);
                }
                byteBuffer.position(limit);
            }
            else {
                final byte[] array3 = new byte[getTempArraySize(i)];
                do {
                    final int min = Math.min(i, array3.length);
                    if (min > 0) {
                        byteBuffer.get(array3, 0, min);
                    }
                    int n4;
                    if (b || i > min) {
                        n4 = this.engineUpdate(array3, 0, min, array, n2);
                    }
                    else {
                        n4 = this.engineDoFinal(array3, 0, min, array, n2);
                    }
                    n += n4;
                    n2 += n4;
                    i -= min;
                } while (i > 0);
            }
            byteBuffer2.position(position2 + n);
        }
        else if (hasArray) {
            final byte[] array4 = byteBuffer.array();
            final int n5 = byteBuffer.arrayOffset() + position;
            byte[] array5;
            if (b) {
                array5 = this.engineUpdate(array4, n5, i);
            }
            else {
                array5 = this.engineDoFinal(array4, n5, i);
            }
            byteBuffer.position(limit);
            if (array5 != null && array5.length != 0) {
                byteBuffer2.put(array5);
                n = array5.length;
            }
        }
        else {
            final byte[] array6 = new byte[getTempArraySize(i)];
            do {
                final int min2 = Math.min(i, array6.length);
                if (min2 > 0) {
                    byteBuffer.get(array6, 0, min2);
                }
                byte[] array7;
                if (b || i > min2) {
                    array7 = this.engineUpdate(array6, 0, min2);
                }
                else {
                    array7 = this.engineDoFinal(array6, 0, min2);
                }
                if (array7 != null && array7.length != 0) {
                    byteBuffer2.put(array7);
                    n += array7.length;
                }
                i -= min2;
            } while (i > 0);
        }
        return n;
    }
    
    protected byte[] engineWrap(final Key key) throws IllegalBlockSizeException, InvalidKeyException {
        throw new UnsupportedOperationException();
    }
    
    protected Key engineUnwrap(final byte[] array, final String s, final int n) throws InvalidKeyException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }
    
    protected int engineGetKeySize(final Key key) throws InvalidKeyException {
        throw new UnsupportedOperationException();
    }
    
    protected void engineUpdateAAD(final byte[] array, final int n, final int n2) {
        throw new UnsupportedOperationException("The underlying Cipher implementation does not support this method");
    }
    
    protected void engineUpdateAAD(final ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("The underlying Cipher implementation does not support this method");
    }
}
