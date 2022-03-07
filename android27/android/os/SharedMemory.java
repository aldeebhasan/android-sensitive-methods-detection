package android.os;

import java.io.*;
import android.system.*;
import java.nio.*;

public final class SharedMemory implements Parcelable, Closeable
{
    public static final Creator<SharedMemory> CREATOR;
    
    SharedMemory() {
        throw new RuntimeException("Stub!");
    }
    
    public static SharedMemory create(final String name, final int size) throws ErrnoException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setProtect(final int prot) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSize() {
        throw new RuntimeException("Stub!");
    }
    
    public ByteBuffer mapReadWrite() throws ErrnoException {
        throw new RuntimeException("Stub!");
    }
    
    public ByteBuffer mapReadOnly() throws ErrnoException {
        throw new RuntimeException("Stub!");
    }
    
    public ByteBuffer map(final int prot, final int offset, final int length) throws ErrnoException {
        throw new RuntimeException("Stub!");
    }
    
    public static void unmap(final ByteBuffer buffer) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
