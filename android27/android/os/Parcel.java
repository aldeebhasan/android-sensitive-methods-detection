package android.os;

import android.util.*;
import java.io.*;
import java.util.*;

public final class Parcel
{
    public static final Parcelable.Creator<String> STRING_CREATOR;
    
    Parcel() {
        throw new RuntimeException("Stub!");
    }
    
    public static Parcel obtain() {
        throw new RuntimeException("Stub!");
    }
    
    public final void recycle() {
        throw new RuntimeException("Stub!");
    }
    
    public final int dataSize() {
        throw new RuntimeException("Stub!");
    }
    
    public final int dataAvail() {
        throw new RuntimeException("Stub!");
    }
    
    public final int dataPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public final int dataCapacity() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataSize(final int size) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataPosition(final int pos) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDataCapacity(final int size) {
        throw new RuntimeException("Stub!");
    }
    
    public final byte[] marshall() {
        throw new RuntimeException("Stub!");
    }
    
    public final void unmarshall(final byte[] data, final int offset, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public final void appendFrom(final Parcel parcel, final int offset, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean hasFileDescriptors() {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeInterfaceToken(final String interfaceName) {
        throw new RuntimeException("Stub!");
    }
    
    public final void enforceInterface(final String interfaceName) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeByteArray(final byte[] b) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeByteArray(final byte[] b, final int offset, final int len) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeInt(final int val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeLong(final long val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeFloat(final float val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeDouble(final double val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeString(final String val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeStrongBinder(final IBinder val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeStrongInterface(final IInterface val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeFileDescriptor(final FileDescriptor val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeByte(final byte val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeMap(final Map val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeBundle(final Bundle val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writePersistableBundle(final PersistableBundle val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeSize(final Size val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeSizeF(final SizeF val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeList(final List val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeArray(final Object[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeSparseArray(final SparseArray<Object> val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeSparseBooleanArray(final SparseBooleanArray val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeBooleanArray(final boolean[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean[] createBooleanArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readBooleanArray(final boolean[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeCharArray(final char[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final char[] createCharArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readCharArray(final char[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeIntArray(final int[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final int[] createIntArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readIntArray(final int[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeLongArray(final long[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final long[] createLongArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readLongArray(final long[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeFloatArray(final float[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final float[] createFloatArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readFloatArray(final float[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeDoubleArray(final double[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final double[] createDoubleArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readDoubleArray(final double[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeStringArray(final String[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final String[] createStringArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readStringArray(final String[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeBinderArray(final IBinder[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final IBinder[] createBinderArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readBinderArray(final IBinder[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Parcelable> void writeTypedList(final List<T> val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeStringList(final List<String> val) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeBinderList(final List<IBinder> val) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Parcelable> void writeTypedArray(final T[] val, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Parcelable> void writeTypedObject(final T val, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeValue(final Object v) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeParcelable(final Parcelable p, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeSerializable(final Serializable s) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeException(final Exception e) {
        throw new RuntimeException("Stub!");
    }
    
    public final void writeNoException() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readException() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readException(final int code, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public final int readInt() {
        throw new RuntimeException("Stub!");
    }
    
    public final long readLong() {
        throw new RuntimeException("Stub!");
    }
    
    public final float readFloat() {
        throw new RuntimeException("Stub!");
    }
    
    public final double readDouble() {
        throw new RuntimeException("Stub!");
    }
    
    public final String readString() {
        throw new RuntimeException("Stub!");
    }
    
    public final IBinder readStrongBinder() {
        throw new RuntimeException("Stub!");
    }
    
    public final ParcelFileDescriptor readFileDescriptor() {
        throw new RuntimeException("Stub!");
    }
    
    public final byte readByte() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readMap(final Map outVal, final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final void readList(final List outVal, final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final HashMap readHashMap(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final Bundle readBundle() {
        throw new RuntimeException("Stub!");
    }
    
    public final Bundle readBundle(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final PersistableBundle readPersistableBundle() {
        throw new RuntimeException("Stub!");
    }
    
    public final PersistableBundle readPersistableBundle(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final Size readSize() {
        throw new RuntimeException("Stub!");
    }
    
    public final SizeF readSizeF() {
        throw new RuntimeException("Stub!");
    }
    
    public final byte[] createByteArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readByteArray(final byte[] val) {
        throw new RuntimeException("Stub!");
    }
    
    public final ArrayList readArrayList(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final Object[] readArray(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final SparseArray readSparseArray(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final SparseBooleanArray readSparseBooleanArray() {
        throw new RuntimeException("Stub!");
    }
    
    public final <T> ArrayList<T> createTypedArrayList(final Parcelable.Creator<T> c) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T> void readTypedList(final List<T> list, final Parcelable.Creator<T> c) {
        throw new RuntimeException("Stub!");
    }
    
    public final ArrayList<String> createStringArrayList() {
        throw new RuntimeException("Stub!");
    }
    
    public final ArrayList<IBinder> createBinderArrayList() {
        throw new RuntimeException("Stub!");
    }
    
    public final void readStringList(final List<String> list) {
        throw new RuntimeException("Stub!");
    }
    
    public final void readBinderList(final List<IBinder> list) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T> T[] createTypedArray(final Parcelable.Creator<T> c) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T> void readTypedArray(final T[] val, final Parcelable.Creator<T> c) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T> T readTypedObject(final Parcelable.Creator<T> c) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Parcelable> void writeParcelableArray(final T[] value, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public final Object readValue(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final <T extends Parcelable> T readParcelable(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final Parcelable[] readParcelableArray(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public final Serializable readSerializable() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    static {
        STRING_CREATOR = null;
    }
}
