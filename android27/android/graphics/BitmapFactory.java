package android.graphics;

import android.content.res.*;
import android.util.*;
import java.io.*;

public class BitmapFactory
{
    public BitmapFactory() {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeFile(final String pathName, final Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeFile(final String pathName) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeResourceStream(final Resources res, final TypedValue value, final InputStream is, final Rect pad, final Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeResource(final Resources res, final int id, final Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeResource(final Resources res, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeByteArray(final byte[] data, final int offset, final int length, final Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeByteArray(final byte[] data, final int offset, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeStream(final InputStream is, final Rect outPadding, final Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeStream(final InputStream is) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeFileDescriptor(final FileDescriptor fd, final Rect outPadding, final Options opts) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap decodeFileDescriptor(final FileDescriptor fd) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Options
    {
        public Bitmap inBitmap;
        public int inDensity;
        @Deprecated
        public boolean inDither;
        @Deprecated
        public boolean inInputShareable;
        public boolean inJustDecodeBounds;
        public boolean inMutable;
        @Deprecated
        public boolean inPreferQualityOverSpeed;
        public ColorSpace inPreferredColorSpace;
        public Bitmap.Config inPreferredConfig;
        public boolean inPremultiplied;
        @Deprecated
        public boolean inPurgeable;
        public int inSampleSize;
        public boolean inScaled;
        public int inScreenDensity;
        public int inTargetDensity;
        public byte[] inTempStorage;
        @Deprecated
        public boolean mCancel;
        public ColorSpace outColorSpace;
        public Bitmap.Config outConfig;
        public int outHeight;
        public String outMimeType;
        public int outWidth;
        
        public Options() {
            this.inTempStorage = null;
            throw new RuntimeException("Stub!");
        }
        
        @Deprecated
        public void requestCancelDecode() {
            throw new RuntimeException("Stub!");
        }
    }
}
