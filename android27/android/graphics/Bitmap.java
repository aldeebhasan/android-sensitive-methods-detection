package android.graphics;

import java.nio.*;
import android.util.*;
import java.io.*;
import android.os.*;

public final class Bitmap implements Parcelable
{
    public static final Creator<Bitmap> CREATOR;
    public static final int DENSITY_NONE = 0;
    
    Bitmap() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDensity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDensity(final int density) {
        throw new RuntimeException("Stub!");
    }
    
    public void reconfigure(final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWidth(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public void setHeight(final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void setConfig(final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public void recycle() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isRecycled() {
        throw new RuntimeException("Stub!");
    }
    
    public int getGenerationId() {
        throw new RuntimeException("Stub!");
    }
    
    public void copyPixelsToBuffer(final Buffer dst) {
        throw new RuntimeException("Stub!");
    }
    
    public void copyPixelsFromBuffer(final Buffer src) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap copy(final Config config, final boolean isMutable) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createScaledBitmap(final Bitmap src, final int dstWidth, final int dstHeight, final boolean filter) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final Bitmap src) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final Bitmap source, final int x, final int y, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final Bitmap source, final int x, final int y, final int width, final int height, final Matrix m, final boolean filter) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final DisplayMetrics display, final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final int width, final int height, final Config config, final boolean hasAlpha) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final int width, final int height, final Config config, final boolean hasAlpha, final ColorSpace colorSpace) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final DisplayMetrics display, final int width, final int height, final Config config, final boolean hasAlpha) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final DisplayMetrics display, final int width, final int height, final Config config, final boolean hasAlpha, final ColorSpace colorSpace) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final int[] colors, final int offset, final int stride, final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final DisplayMetrics display, final int[] colors, final int offset, final int stride, final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final int[] colors, final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap createBitmap(final DisplayMetrics display, final int[] colors, final int width, final int height, final Config config) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getNinePatchChunk() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean compress(final CompressFormat format, final int quality, final OutputStream stream) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isMutable() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isPremultiplied() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setPremultiplied(final boolean premultiplied) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getScaledWidth(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public int getScaledHeight(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public int getScaledWidth(final DisplayMetrics metrics) {
        throw new RuntimeException("Stub!");
    }
    
    public int getScaledHeight(final DisplayMetrics metrics) {
        throw new RuntimeException("Stub!");
    }
    
    public int getScaledWidth(final int targetDensity) {
        throw new RuntimeException("Stub!");
    }
    
    public int getScaledHeight(final int targetDensity) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getRowBytes() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getByteCount() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getAllocationByteCount() {
        throw new RuntimeException("Stub!");
    }
    
    public final Config getConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean hasAlpha() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHasAlpha(final boolean hasAlpha) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean hasMipMap() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setHasMipMap(final boolean hasMipMap) {
        throw new RuntimeException("Stub!");
    }
    
    public final ColorSpace getColorSpace() {
        throw new RuntimeException("Stub!");
    }
    
    public void eraseColor(final int c) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPixel(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPixel(final int x, final int y, final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel p, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap extractAlpha() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap extractAlpha(final Paint paint, final int[] offsetXY) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean sameAs(final Bitmap other) {
        throw new RuntimeException("Stub!");
    }
    
    public void prepareToDraw() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public enum Config
    {
        ALPHA_8, 
        ARGB_4444, 
        ARGB_8888, 
        HARDWARE, 
        RGBA_F16, 
        RGB_565;
    }
    
    public enum CompressFormat
    {
        JPEG, 
        PNG, 
        WEBP;
    }
}
