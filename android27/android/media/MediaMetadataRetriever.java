package android.media;

import java.util.*;
import java.io.*;
import android.content.*;
import android.net.*;
import android.graphics.*;

public class MediaMetadataRetriever
{
    public static final int METADATA_KEY_ALBUM = 1;
    public static final int METADATA_KEY_ALBUMARTIST = 13;
    public static final int METADATA_KEY_ARTIST = 2;
    public static final int METADATA_KEY_AUTHOR = 3;
    public static final int METADATA_KEY_BITRATE = 20;
    public static final int METADATA_KEY_CAPTURE_FRAMERATE = 25;
    public static final int METADATA_KEY_CD_TRACK_NUMBER = 0;
    public static final int METADATA_KEY_COMPILATION = 15;
    public static final int METADATA_KEY_COMPOSER = 4;
    public static final int METADATA_KEY_DATE = 5;
    public static final int METADATA_KEY_DISC_NUMBER = 14;
    public static final int METADATA_KEY_DURATION = 9;
    public static final int METADATA_KEY_GENRE = 6;
    public static final int METADATA_KEY_HAS_AUDIO = 16;
    public static final int METADATA_KEY_HAS_VIDEO = 17;
    public static final int METADATA_KEY_LOCATION = 23;
    public static final int METADATA_KEY_MIMETYPE = 12;
    public static final int METADATA_KEY_NUM_TRACKS = 10;
    public static final int METADATA_KEY_TITLE = 7;
    public static final int METADATA_KEY_VIDEO_HEIGHT = 19;
    public static final int METADATA_KEY_VIDEO_ROTATION = 24;
    public static final int METADATA_KEY_VIDEO_WIDTH = 18;
    public static final int METADATA_KEY_WRITER = 11;
    public static final int METADATA_KEY_YEAR = 8;
    public static final int OPTION_CLOSEST = 3;
    public static final int OPTION_CLOSEST_SYNC = 2;
    public static final int OPTION_NEXT_SYNC = 1;
    public static final int OPTION_PREVIOUS_SYNC = 0;
    
    public MediaMetadataRetriever() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDataSource(final String path) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDataSource(final String uri, final Map<String, String> headers) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public native void setDataSource(final FileDescriptor p0, final long p1, final long p2) throws IllegalArgumentException;
    
    public void setDataSource(final FileDescriptor fd) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDataSource(final Context context, final Uri uri) throws IllegalArgumentException, SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDataSource(final MediaDataSource dataSource) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public native String extractMetadata(final int p0);
    
    public Bitmap getFrameAtTime(final long timeUs, final int option) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getScaledFrameAtTime(final long timeUs, final int option, final int dstWidth, final int dstHeight) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getFrameAtTime(final long timeUs) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getFrameAtTime() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getEmbeddedPicture() {
        throw new RuntimeException("Stub!");
    }
    
    public native void release();
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
