package android.media;

import java.io.*;
import android.view.*;
import java.nio.*;
import android.os.*;

public final class MediaCodec
{
    public static final int BUFFER_FLAG_CODEC_CONFIG = 2;
    public static final int BUFFER_FLAG_END_OF_STREAM = 4;
    public static final int BUFFER_FLAG_KEY_FRAME = 1;
    public static final int BUFFER_FLAG_PARTIAL_FRAME = 8;
    @Deprecated
    public static final int BUFFER_FLAG_SYNC_FRAME = 1;
    public static final int CONFIGURE_FLAG_ENCODE = 1;
    public static final int CRYPTO_MODE_AES_CBC = 2;
    public static final int CRYPTO_MODE_AES_CTR = 1;
    public static final int CRYPTO_MODE_UNENCRYPTED = 0;
    @Deprecated
    public static final int INFO_OUTPUT_BUFFERS_CHANGED = -3;
    public static final int INFO_OUTPUT_FORMAT_CHANGED = -2;
    public static final int INFO_TRY_AGAIN_LATER = -1;
    public static final String PARAMETER_KEY_REQUEST_SYNC_FRAME = "request-sync";
    public static final String PARAMETER_KEY_SUSPEND = "drop-input-frames";
    public static final String PARAMETER_KEY_VIDEO_BITRATE = "video-bitrate";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    
    MediaCodec() {
        throw new RuntimeException("Stub!");
    }
    
    public static MediaCodec createDecoderByType(final String type) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static MediaCodec createEncoderByType(final String type) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static MediaCodec createByCodecName(final String name) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
    
    public final void reset() {
        throw new RuntimeException("Stub!");
    }
    
    public final void release() {
        throw new RuntimeException("Stub!");
    }
    
    public void configure(final MediaFormat format, final Surface surface, final MediaCrypto crypto, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void configure(final MediaFormat format, final Surface surface, final int flags, final MediaDescrambler descrambler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOutputSurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public static Surface createPersistentInputSurface() {
        throw new RuntimeException("Stub!");
    }
    
    public void setInputSurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public final native Surface createInputSurface();
    
    public final void start() {
        throw new RuntimeException("Stub!");
    }
    
    public final void stop() {
        throw new RuntimeException("Stub!");
    }
    
    public final void flush() {
        throw new RuntimeException("Stub!");
    }
    
    public final void queueInputBuffer(final int index, final int offset, final int size, final long presentationTimeUs, final int flags) throws CryptoException {
        throw new RuntimeException("Stub!");
    }
    
    public final void queueSecureInputBuffer(final int index, final int offset, final CryptoInfo info, final long presentationTimeUs, final int flags) throws CryptoException {
        throw new RuntimeException("Stub!");
    }
    
    public final int dequeueInputBuffer(final long timeoutUs) {
        throw new RuntimeException("Stub!");
    }
    
    public final int dequeueOutputBuffer(final BufferInfo info, final long timeoutUs) {
        throw new RuntimeException("Stub!");
    }
    
    public final void releaseOutputBuffer(final int index, final boolean render) {
        throw new RuntimeException("Stub!");
    }
    
    public final void releaseOutputBuffer(final int index, final long renderTimestampNs) {
        throw new RuntimeException("Stub!");
    }
    
    public final native void signalEndOfInputStream();
    
    public final MediaFormat getOutputFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public final MediaFormat getInputFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public final MediaFormat getOutputFormat(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public ByteBuffer[] getInputBuffers() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public ByteBuffer[] getOutputBuffers() {
        throw new RuntimeException("Stub!");
    }
    
    public ByteBuffer getInputBuffer(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public Image getInputImage(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public ByteBuffer getOutputBuffer(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public Image getOutputImage(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public final native void setVideoScalingMode(final int p0);
    
    public final native String getName();
    
    public PersistableBundle getMetrics() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setParameters(final Bundle params) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallback(final Callback cb, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallback(final Callback cb) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnFrameRenderedListener(final OnFrameRenderedListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public MediaCodecInfo getCodecInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class BufferInfo
    {
        public int flags;
        public int offset;
        public long presentationTimeUs;
        public int size;
        
        public BufferInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public void set(final int newOffset, final int newSize, final long newTimeUs, final int newFlags) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class CodecException extends IllegalStateException
    {
        public static final int ERROR_INSUFFICIENT_RESOURCE = 1100;
        public static final int ERROR_RECLAIMED = 1101;
        
        CodecException() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isTransient() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isRecoverable() {
            throw new RuntimeException("Stub!");
        }
        
        public int getErrorCode() {
            throw new RuntimeException("Stub!");
        }
        
        public String getDiagnosticInfo() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class CryptoException extends RuntimeException
    {
        public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
        public static final int ERROR_KEY_EXPIRED = 2;
        public static final int ERROR_NO_KEY = 1;
        public static final int ERROR_RESOURCE_BUSY = 3;
        public static final int ERROR_SESSION_NOT_OPENED = 5;
        public static final int ERROR_UNSUPPORTED_OPERATION = 6;
        
        public CryptoException(final int errorCode, final String detailMessage) {
            throw new RuntimeException("Stub!");
        }
        
        public int getErrorCode() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class CryptoInfo
    {
        public byte[] iv;
        public byte[] key;
        public int mode;
        public int[] numBytesOfClearData;
        public int[] numBytesOfEncryptedData;
        public int numSubSamples;
        
        public CryptoInfo() {
            this.iv = null;
            this.key = null;
            this.numBytesOfClearData = null;
            this.numBytesOfEncryptedData = null;
            throw new RuntimeException("Stub!");
        }
        
        public void set(final int newNumSubSamples, final int[] newNumBytesOfClearData, final int[] newNumBytesOfEncryptedData, final byte[] newKey, final byte[] newIV, final int newMode) {
            throw new RuntimeException("Stub!");
        }
        
        public void setPattern(final Pattern newPattern) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        public static final class Pattern
        {
            public Pattern(final int blocksToEncrypt, final int blocksToSkip) {
                throw new RuntimeException("Stub!");
            }
            
            public void set(final int blocksToEncrypt, final int blocksToSkip) {
                throw new RuntimeException("Stub!");
            }
            
            public int getSkipBlocks() {
                throw new RuntimeException("Stub!");
            }
            
            public int getEncryptBlocks() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onInputBufferAvailable(final MediaCodec p0, final int p1);
        
        public abstract void onOutputBufferAvailable(final MediaCodec p0, final int p1, final BufferInfo p2);
        
        public abstract void onError(final MediaCodec p0, final CodecException p1);
        
        public abstract void onOutputFormatChanged(final MediaCodec p0, final MediaFormat p1);
    }
    
    public static final class MetricsConstants
    {
        public static final String CODEC = "android.media.mediacodec.codec";
        public static final String ENCODER = "android.media.mediacodec.encoder";
        public static final String HEIGHT = "android.media.mediacodec.height";
        public static final String MIME_TYPE = "android.media.mediacodec.mime";
        public static final String MODE = "android.media.mediacodec.mode";
        public static final String MODE_AUDIO = "audio";
        public static final String MODE_VIDEO = "video";
        public static final String ROTATION = "android.media.mediacodec.rotation";
        public static final String SECURE = "android.media.mediacodec.secure";
        public static final String WIDTH = "android.media.mediacodec.width";
        
        MetricsConstants() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnFrameRenderedListener
    {
        void onFrameRendered(final MediaCodec p0, final long p1, final long p2);
    }
}
