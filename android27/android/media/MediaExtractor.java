package android.media;

import android.content.*;
import android.net.*;
import android.content.res.*;
import java.io.*;
import java.util.*;
import java.nio.*;
import android.os.*;

public final class MediaExtractor
{
    public static final int SAMPLE_FLAG_ENCRYPTED = 2;
    public static final int SAMPLE_FLAG_PARTIAL_FRAME = 4;
    public static final int SAMPLE_FLAG_SYNC = 1;
    public static final int SEEK_TO_CLOSEST_SYNC = 2;
    public static final int SEEK_TO_NEXT_SYNC = 1;
    public static final int SEEK_TO_PREVIOUS_SYNC = 0;
    
    public MediaExtractor() {
        throw new RuntimeException("Stub!");
    }
    
    public final native void setDataSource(final MediaDataSource p0) throws IOException;
    
    public final void setDataSource(final Context context, final Uri uri, final Map<String, String> headers) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataSource(final String path, final Map<String, String> headers) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataSource(final String path) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataSource(final AssetFileDescriptor afd) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataSource(final FileDescriptor fd) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final native void setDataSource(final FileDescriptor p0, final long p1, final long p2) throws IOException;
    
    public final void setMediaCas(final MediaCas mediaCas) {
        throw new RuntimeException("Stub!");
    }
    
    public CasInfo getCasInfo(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
    
    public final native void release();
    
    public final native int getTrackCount();
    
    public DrmInitData getDrmInitData() {
        throw new RuntimeException("Stub!");
    }
    
    public Map<UUID, byte[]> getPsshInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaFormat getTrackFormat(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public native void selectTrack(final int p0);
    
    public native void unselectTrack(final int p0);
    
    public native void seekTo(final long p0, final int p1);
    
    public native boolean advance();
    
    public native int readSampleData(final ByteBuffer p0, final int p1);
    
    public native int getSampleTrackIndex();
    
    public native long getSampleTime();
    
    public native int getSampleFlags();
    
    public native boolean getSampleCryptoInfo(final MediaCodec.CryptoInfo p0);
    
    public native long getCachedDuration();
    
    public native boolean hasCacheReachedEndOfStream();
    
    public PersistableBundle getMetrics() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class CasInfo
    {
        CasInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSystemId() {
            throw new RuntimeException("Stub!");
        }
        
        public MediaCas.Session getSession() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class MetricsConstants
    {
        public static final String FORMAT = "android.media.mediaextractor.fmt";
        public static final String MIME_TYPE = "android.media.mediaextractor.mime";
        public static final String TRACKS = "android.media.mediaextractor.ntrk";
        
        MetricsConstants() {
            throw new RuntimeException("Stub!");
        }
    }
}
