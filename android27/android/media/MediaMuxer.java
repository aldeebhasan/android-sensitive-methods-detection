package android.media;

import java.io.*;
import java.nio.*;

public final class MediaMuxer
{
    public MediaMuxer(final String path, final int format) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public MediaMuxer(final FileDescriptor fd, final int format) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void setOrientationHint(final int degrees) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLocation(final float latitude, final float longitude) {
        throw new RuntimeException("Stub!");
    }
    
    public void start() {
        throw new RuntimeException("Stub!");
    }
    
    public void stop() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public int addTrack(final MediaFormat format) {
        throw new RuntimeException("Stub!");
    }
    
    public void writeSampleData(final int trackIndex, final ByteBuffer byteBuf, final MediaCodec.BufferInfo bufferInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public void release() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class OutputFormat
    {
        public static final int MUXER_OUTPUT_3GPP = 2;
        public static final int MUXER_OUTPUT_MPEG_4 = 0;
        public static final int MUXER_OUTPUT_WEBM = 1;
        
        OutputFormat() {
            throw new RuntimeException("Stub!");
        }
    }
}
