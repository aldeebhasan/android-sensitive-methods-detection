package android.graphics;

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
