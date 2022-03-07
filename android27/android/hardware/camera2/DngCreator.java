package android.hardware.camera2;

import android.graphics.*;
import android.media.*;
import android.location.*;
import android.util.*;
import java.io.*;
import java.nio.*;

public final class DngCreator implements AutoCloseable
{
    public static final int MAX_THUMBNAIL_DIMENSION = 256;
    
    public DngCreator(final CameraCharacteristics characteristics, final CaptureResult metadata) {
        throw new RuntimeException("Stub!");
    }
    
    public DngCreator setOrientation(final int orientation) {
        throw new RuntimeException("Stub!");
    }
    
    public DngCreator setThumbnail(final Bitmap pixels) {
        throw new RuntimeException("Stub!");
    }
    
    public DngCreator setThumbnail(final Image pixels) {
        throw new RuntimeException("Stub!");
    }
    
    public DngCreator setLocation(final Location location) {
        throw new RuntimeException("Stub!");
    }
    
    public DngCreator setDescription(final String description) {
        throw new RuntimeException("Stub!");
    }
    
    public void writeInputStream(final OutputStream dngOutput, final Size size, final InputStream pixels, final long offset) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void writeByteBuffer(final OutputStream dngOutput, final Size size, final ByteBuffer pixels, final long offset) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void writeImage(final OutputStream dngOutput, final Image pixels) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
