package java.security;

import java.security.spec.*;
import java.nio.*;
import sun.security.jca.*;

public abstract class SignatureSpi
{
    protected SecureRandom appRandom;
    
    public SignatureSpi() {
        this.appRandom = null;
    }
    
    protected abstract void engineInitVerify(final PublicKey p0) throws InvalidKeyException;
    
    void engineInitVerify(final PublicKey publicKey, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            try {
                this.engineSetParameter(algorithmParameterSpec);
            }
            catch (UnsupportedOperationException ex) {
                throw new InvalidAlgorithmParameterException(ex);
            }
        }
        this.engineInitVerify(publicKey);
    }
    
    protected abstract void engineInitSign(final PrivateKey p0) throws InvalidKeyException;
    
    protected void engineInitSign(final PrivateKey privateKey, final SecureRandom appRandom) throws InvalidKeyException {
        this.appRandom = appRandom;
        this.engineInitSign(privateKey);
    }
    
    void engineInitSign(final PrivateKey privateKey, final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            try {
                this.engineSetParameter(algorithmParameterSpec);
            }
            catch (UnsupportedOperationException ex) {
                throw new InvalidAlgorithmParameterException(ex);
            }
        }
        this.engineInitSign(privateKey, secureRandom);
    }
    
    protected abstract void engineUpdate(final byte p0) throws SignatureException;
    
    protected abstract void engineUpdate(final byte[] p0, final int p1, final int p2) throws SignatureException;
    
    protected void engineUpdate(final ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return;
        }
        try {
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
                final byte[] array2 = new byte[JCAUtil.getTempArraySize(i)];
                while (i > 0) {
                    final int min = Math.min(i, array2.length);
                    byteBuffer.get(array2, 0, min);
                    this.engineUpdate(array2, 0, min);
                    i -= min;
                }
            }
        }
        catch (SignatureException ex) {
            throw new ProviderException("update() failed", ex);
        }
    }
    
    protected abstract byte[] engineSign() throws SignatureException;
    
    protected int engineSign(final byte[] array, final int n, final int n2) throws SignatureException {
        final byte[] engineSign = this.engineSign();
        if (n2 < engineSign.length) {
            throw new SignatureException("partial signatures not returned");
        }
        if (array.length - n < engineSign.length) {
            throw new SignatureException("insufficient space in the output buffer to store the signature");
        }
        System.arraycopy(engineSign, 0, array, n, engineSign.length);
        return engineSign.length;
    }
    
    protected abstract boolean engineVerify(final byte[] p0) throws SignatureException;
    
    protected boolean engineVerify(final byte[] array, final int n, final int n2) throws SignatureException {
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return this.engineVerify(array2);
    }
    
    @Deprecated
    protected abstract void engineSetParameter(final String p0, final Object p1) throws InvalidParameterException;
    
    protected void engineSetParameter(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        throw new UnsupportedOperationException();
    }
    
    protected AlgorithmParameters engineGetParameters() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    protected abstract Object engineGetParameter(final String p0) throws InvalidParameterException;
    
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }
}
