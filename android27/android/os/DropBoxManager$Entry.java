package android.os;

import java.io.*;

public static class Entry implements Parcelable, Closeable
{
    public static final Creator<Entry> CREATOR;
    
    public Entry(final String tag, final long millis) {
        throw new RuntimeException("Stub!");
    }
    
    public Entry(final String tag, final long millis, final String text) {
        throw new RuntimeException("Stub!");
    }
    
    public Entry(final String tag, final long millis, final byte[] data, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Entry(final String tag, final long millis, final ParcelFileDescriptor data, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Entry(final String tag, final long millis, final File data, final int flags) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public String getTag() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimeMillis() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public String getText(final int maxBytes) {
        throw new RuntimeException("Stub!");
    }
    
    public InputStream getInputStream() throws IOException {
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
    
    static {
        CREATOR = null;
    }
}
