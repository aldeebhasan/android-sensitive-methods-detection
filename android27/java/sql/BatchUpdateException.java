package java.sql;

import java.util.*;
import java.io.*;

public class BatchUpdateException extends SQLException
{
    private int[] updateCounts;
    private long[] longUpdateCounts;
    private static final long serialVersionUID = 5977529877145521757L;
    
    public BatchUpdateException(final String s, final String s2, final int n, final int[] array) {
        super(s, s2, n);
        this.updateCounts = (int[])((array == null) ? null : Arrays.copyOf(array, array.length));
        this.longUpdateCounts = (long[])((array == null) ? null : copyUpdateCount(array));
    }
    
    public BatchUpdateException(final String s, final String s2, final int[] array) {
        this(s, s2, 0, array);
    }
    
    public BatchUpdateException(final String s, final int[] array) {
        this(s, null, 0, array);
    }
    
    public BatchUpdateException(final int[] array) {
        this(null, null, 0, array);
    }
    
    public BatchUpdateException() {
        this(null, null, 0, (int[])null);
    }
    
    public BatchUpdateException(final Throwable t) {
        this((t == null) ? null : t.toString(), null, 0, (int[])null, t);
    }
    
    public BatchUpdateException(final int[] array, final Throwable t) {
        this((t == null) ? null : t.toString(), null, 0, array, t);
    }
    
    public BatchUpdateException(final String s, final int[] array, final Throwable t) {
        this(s, null, 0, array, t);
    }
    
    public BatchUpdateException(final String s, final String s2, final int[] array, final Throwable t) {
        this(s, s2, 0, array, t);
    }
    
    public BatchUpdateException(final String s, final String s2, final int n, final int[] array, final Throwable t) {
        super(s, s2, n, t);
        this.updateCounts = (int[])((array == null) ? null : Arrays.copyOf(array, array.length));
        this.longUpdateCounts = (long[])((array == null) ? null : copyUpdateCount(array));
    }
    
    public int[] getUpdateCounts() {
        return (int[])((this.updateCounts == null) ? null : Arrays.copyOf(this.updateCounts, this.updateCounts.length));
    }
    
    public BatchUpdateException(final String s, final String s2, final int n, final long[] array, final Throwable t) {
        super(s, s2, n, t);
        this.longUpdateCounts = (long[])((array == null) ? null : Arrays.copyOf(array, array.length));
        this.updateCounts = (int[])((this.longUpdateCounts == null) ? null : copyUpdateCount(this.longUpdateCounts));
    }
    
    public long[] getLargeUpdateCounts() {
        return (long[])((this.longUpdateCounts == null) ? null : Arrays.copyOf(this.longUpdateCounts, this.longUpdateCounts.length));
    }
    
    private static long[] copyUpdateCount(final int[] array) {
        final long[] array2 = new long[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    private static int[] copyUpdateCount(final long[] array) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = (int)array[i];
        }
        return array2;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final int[] array = (int[])fields.get("updateCounts", null);
        final long[] array2 = (long[])fields.get("longUpdateCounts", null);
        if (array != null && array2 != null && array.length != array2.length) {
            throw new InvalidObjectException("update counts are not the expected size");
        }
        if (array != null) {
            this.updateCounts = array.clone();
        }
        if (array2 != null) {
            this.longUpdateCounts = array2.clone();
        }
        if (this.updateCounts == null && this.longUpdateCounts != null) {
            this.updateCounts = copyUpdateCount(this.longUpdateCounts);
        }
        if (this.longUpdateCounts == null && this.updateCounts != null) {
            this.longUpdateCounts = copyUpdateCount(this.updateCounts);
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("updateCounts", this.updateCounts);
        putFields.put("longUpdateCounts", this.longUpdateCounts);
        objectOutputStream.writeFields();
    }
}
