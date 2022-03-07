package android.os;

import java.util.*;
import android.util.*;
import java.io.*;

public final class Bundle extends BaseBundle implements Cloneable, Parcelable
{
    public static final Creator<Bundle> CREATOR;
    public static final Bundle EMPTY;
    
    public Bundle() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle(final int capacity) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle(final Bundle b) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle(final PersistableBundle b) {
        throw new RuntimeException("Stub!");
    }
    
    public void setClassLoader(final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public ClassLoader getClassLoader() {
        throw new RuntimeException("Stub!");
    }
    
    public Object clone() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle deepCopy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void clear() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void remove(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public void putAll(final Bundle bundle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasFileDescriptors() {
        throw new RuntimeException("Stub!");
    }
    
    public void putByte(final String key, final byte value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putChar(final String key, final char value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putShort(final String key, final short value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putFloat(final String key, final float value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putCharSequence(final String key, final CharSequence value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putParcelable(final String key, final Parcelable value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putSize(final String key, final Size value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putSizeF(final String key, final SizeF value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putParcelableArray(final String key, final Parcelable[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putParcelableArrayList(final String key, final ArrayList<? extends Parcelable> value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putSparseParcelableArray(final String key, final SparseArray<? extends Parcelable> value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putIntegerArrayList(final String key, final ArrayList<Integer> value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putStringArrayList(final String key, final ArrayList<String> value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putCharSequenceArrayList(final String key, final ArrayList<CharSequence> value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putSerializable(final String key, final Serializable value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putByteArray(final String key, final byte[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putShortArray(final String key, final short[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putCharArray(final String key, final char[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putFloatArray(final String key, final float[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putCharSequenceArray(final String key, final CharSequence[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putBundle(final String key, final Bundle value) {
        throw new RuntimeException("Stub!");
    }
    
    public void putBinder(final String key, final IBinder value) {
        throw new RuntimeException("Stub!");
    }
    
    public byte getByte(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public Byte getByte(final String key, final byte defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    public char getChar(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public char getChar(final String key, final char defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    public short getShort(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public short getShort(final String key, final short defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    public float getFloat(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public float getFloat(final String key, final float defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getCharSequence(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getCharSequence(final String key, final CharSequence defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    public Size getSize(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public SizeF getSizeF(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getBundle(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends Parcelable> T getParcelable(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public Parcelable[] getParcelableArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public Serializable getSerializable(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayList<Integer> getIntegerArrayList(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayList<String> getStringArrayList(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayList<CharSequence> getCharSequenceArrayList(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getByteArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public short[] getShortArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public char[] getCharArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public float[] getFloatArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence[] getCharSequenceArray(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public IBinder getBinder(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void readFromParcel(final Parcel parcel) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
        EMPTY = null;
    }
}
