package android.renderscript;

import android.graphics.*;
import java.nio.*;
import android.view.*;
import android.content.res.*;

public class Allocation extends BaseObj
{
    public static final int USAGE_GRAPHICS_CONSTANTS = 8;
    public static final int USAGE_GRAPHICS_RENDER_TARGET = 16;
    public static final int USAGE_GRAPHICS_TEXTURE = 2;
    public static final int USAGE_GRAPHICS_VERTEX = 4;
    public static final int USAGE_IO_INPUT = 32;
    public static final int USAGE_IO_OUTPUT = 64;
    public static final int USAGE_SCRIPT = 1;
    public static final int USAGE_SHARED = 128;
    
    Allocation() {
        throw new RuntimeException("Stub!");
    }
    
    public Element getElement() {
        throw new RuntimeException("Stub!");
    }
    
    public int getUsage() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAutoPadding(final boolean useAutoPadding) {
        throw new RuntimeException("Stub!");
    }
    
    public int getBytesSize() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public Type getType() {
        throw new RuntimeException("Stub!");
    }
    
    public void syncAll(final int srcLocation) {
        throw new RuntimeException("Stub!");
    }
    
    public void ioSend() {
        throw new RuntimeException("Stub!");
    }
    
    public void ioReceive() {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final BaseObj[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFromUnchecked(final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFromUnchecked(final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFromUnchecked(final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFromUnchecked(final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFromUnchecked(final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final Bitmap b) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyFrom(final Allocation a) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFromFieldPacker(final int xoff, final FieldPacker fp) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFromFieldPacker(final int xoff, final int component_number, final FieldPacker fp) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFromFieldPacker(final int xoff, final int yoff, final int zoff, final int component_number, final FieldPacker fp) {
        throw new RuntimeException("Stub!");
    }
    
    public void generateMipmaps() {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFromUnchecked(final int off, final int count, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFromUnchecked(final int off, final int count, final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFromUnchecked(final int off, final int count, final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFromUnchecked(final int off, final int count, final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFromUnchecked(final int off, final int count, final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFrom(final int off, final int count, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFrom(final int off, final int count, final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFrom(final int off, final int count, final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFrom(final int off, final int count, final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFrom(final int off, final int count, final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeFrom(final int off, final int count, final Allocation data, final int dataOff) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final int w, final int h, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final int w, final int h, final byte[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final int w, final int h, final short[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final int w, final int h, final int[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final int w, final int h, final float[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final int w, final int h, final Allocation data, final int dataXoff, final int dataYoff) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeFrom(final int xoff, final int yoff, final Bitmap data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy3DRangeFrom(final int xoff, final int yoff, final int zoff, final int w, final int h, final int d, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy3DRangeFrom(final int xoff, final int yoff, final int zoff, final int w, final int h, final int d, final Allocation data, final int dataXoff, final int dataYoff, final int dataZoff) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyTo(final Bitmap b) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyTo(final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyTo(final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyTo(final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyTo(final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyTo(final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public synchronized void resize(final int dimX) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeToUnchecked(final int off, final int count, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeToUnchecked(final int off, final int count, final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeToUnchecked(final int off, final int count, final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeToUnchecked(final int off, final int count, final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeToUnchecked(final int off, final int count, final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeTo(final int off, final int count, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeTo(final int off, final int count, final int[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeTo(final int off, final int count, final short[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeTo(final int off, final int count, final byte[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy1DRangeTo(final int off, final int count, final float[] d) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeTo(final int xoff, final int yoff, final int w, final int h, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeTo(final int xoff, final int yoff, final int w, final int h, final byte[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeTo(final int xoff, final int yoff, final int w, final int h, final short[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeTo(final int xoff, final int yoff, final int w, final int h, final int[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy2DRangeTo(final int xoff, final int yoff, final int w, final int h, final float[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void copy3DRangeTo(final int xoff, final int yoff, final int zoff, final int w, final int h, final int d, final Object array) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createTyped(final RenderScript rs, final Type type, final MipmapControl mips, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createTyped(final RenderScript rs, final Type type, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createTyped(final RenderScript rs, final Type type) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createSized(final RenderScript rs, final Element e, final int count, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createSized(final RenderScript rs, final Element e, final int count) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createFromBitmap(final RenderScript rs, final Bitmap b, final MipmapControl mips, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public ByteBuffer getByteBuffer() {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation[] createAllocations(final RenderScript rs, final Type t, final int usage, final int numAlloc) {
        throw new RuntimeException("Stub!");
    }
    
    public long getStride() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimeStamp() {
        throw new RuntimeException("Stub!");
    }
    
    public Surface getSurface() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSurface(final Surface sur) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createFromBitmap(final RenderScript rs, final Bitmap b) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createCubemapFromBitmap(final RenderScript rs, final Bitmap b, final MipmapControl mips, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createCubemapFromBitmap(final RenderScript rs, final Bitmap b) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createCubemapFromCubeFaces(final RenderScript rs, final Bitmap xpos, final Bitmap xneg, final Bitmap ypos, final Bitmap yneg, final Bitmap zpos, final Bitmap zneg, final MipmapControl mips, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createCubemapFromCubeFaces(final RenderScript rs, final Bitmap xpos, final Bitmap xneg, final Bitmap ypos, final Bitmap yneg, final Bitmap zpos, final Bitmap zneg) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createFromBitmapResource(final RenderScript rs, final Resources res, final int id, final MipmapControl mips, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createFromBitmapResource(final RenderScript rs, final Resources res, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public static Allocation createFromString(final RenderScript rs, final String str, final int usage) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnBufferAvailableListener(final OnBufferAvailableListener callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void destroy() {
        throw new RuntimeException("Stub!");
    }
    
    public enum MipmapControl
    {
        MIPMAP_FULL, 
        MIPMAP_NONE, 
        MIPMAP_ON_SYNC_TO_TEXTURE;
    }
    
    public interface OnBufferAvailableListener
    {
        void onBufferAvailable(final Allocation p0);
    }
}
