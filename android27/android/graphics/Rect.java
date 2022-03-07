package android.graphics;

import android.os.*;

public final class Rect implements Parcelable
{
    public static final Creator<Rect> CREATOR;
    public int bottom;
    public int left;
    public int right;
    public int top;
    
    public Rect() {
        throw new RuntimeException("Stub!");
    }
    
    public Rect(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public Rect(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public String toShortString() {
        throw new RuntimeException("Stub!");
    }
    
    public String flattenToString() {
        throw new RuntimeException("Stub!");
    }
    
    public static Rect unflattenFromString(final String str) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }
    
    public final int width() {
        throw new RuntimeException("Stub!");
    }
    
    public final int height() {
        throw new RuntimeException("Stub!");
    }
    
    public final int centerX() {
        throw new RuntimeException("Stub!");
    }
    
    public final int centerY() {
        throw new RuntimeException("Stub!");
    }
    
    public final float exactCenterX() {
        throw new RuntimeException("Stub!");
    }
    
    public final float exactCenterY() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEmpty() {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final Rect src) {
        throw new RuntimeException("Stub!");
    }
    
    public void offset(final int dx, final int dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void offsetTo(final int newLeft, final int newTop) {
        throw new RuntimeException("Stub!");
    }
    
    public void inset(final int dx, final int dy) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean contains(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean contains(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean contains(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean intersect(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean intersect(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setIntersect(final Rect a, final Rect b) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean intersects(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean intersects(final Rect a, final Rect b) {
        throw new RuntimeException("Stub!");
    }
    
    public void union(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void union(final Rect r) {
        throw new RuntimeException("Stub!");
    }
    
    public void union(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public void sort() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void readFromParcel(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
