package android.graphics;

import android.os.*;

public class Region implements Parcelable
{
    public static final Creator<Region> CREATOR;
    
    public Region() {
        throw new RuntimeException("Stub!");
    }
    
    public Region(final Region region) {
        throw new RuntimeException("Stub!");
    }
    
    public Region(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public Region(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEmpty() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean set(final Region region) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean set(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean set(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPath(final Path path, final Region clip) {
        throw new RuntimeException("Stub!");
    }
    
    public native boolean isEmpty();
    
    public native boolean isRect();
    
    public native boolean isComplex();
    
    public Rect getBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getBounds(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public Path getBoundaryPath() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getBoundaryPath(final Path path) {
        throw new RuntimeException("Stub!");
    }
    
    public native boolean contains(final int p0, final int p1);
    
    public boolean quickContains(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public native boolean quickContains(final int p0, final int p1, final int p2, final int p3);
    
    public boolean quickReject(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public native boolean quickReject(final int p0, final int p1, final int p2, final int p3);
    
    public native boolean quickReject(final Region p0);
    
    public void translate(final int dx, final int dy) {
        throw new RuntimeException("Stub!");
    }
    
    public native void translate(final int p0, final int p1, final Region p2);
    
    public final boolean union(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final Rect r, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final int left, final int top, final int right, final int bottom, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final Region region, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final Rect rect, final Region region, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final Region region1, final Region region2, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public enum Op
    {
        DIFFERENCE, 
        INTERSECT, 
        REPLACE, 
        REVERSE_DIFFERENCE, 
        UNION, 
        XOR;
    }
}
